package isamrs.tim17.lotus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.RoomDTO;
import isamrs.tim17.lotus.dto.RoomDateDTO;
import isamrs.tim17.lotus.dto.RoomTermDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.util.DateUtil;

@RestController
@RequestMapping("/api")
public class RoomController {

	@Autowired
	private RoomService service;
	@Autowired
	private AppointmentService appService;

	/**
	 * This method is used for adding a room.
	 * 
	 * @param room This is a doctor object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PostMapping("/rooms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Room> addRoom(@RequestBody String name) {

		if(name == null || "".equals(name))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Room room = new Room(name);
		service.save(room);
		return new ResponseEntity<>(room, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a list of rooms.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of rooms and the HTTP status
	 *         code.
	 */
	@GetMapping("/rooms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<RoomDTO>> getAllRooms() {
		List<Room> rooms = service.findAll();

		// convert rooms to DTOs
		List<RoomDTO> roomsDTO = new ArrayList<>();
		for (Room r : rooms) {
			roomsDTO.add(new RoomDTO(r));
		}
		return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a room.
	 * 
	 * @param id This is requested room's id.
	 * @return Room This returns the requested room.
	 */
	@GetMapping("/rooms/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") long id) {

		Room room = service.findOne(id);
		RoomDTO dto = new RoomDTO(room);
		// room must exist
		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	/**
	 * This method is used for editing a room.
	 * 
	 * @param room This is a room object from the HTTP request.
	 * @param id   This is the id of the edited room.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/rooms/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Room> updateRoom(@RequestBody RoomDTO newRoom, @PathVariable long id) {

		if(isEmptyOrNull(newRoom))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if (id != newRoom.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// a room must exist
		Room room = service.findOne(newRoom.getId());

		if (room == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		room.setName(newRoom.getName());
		room = service.save(room);
		return new ResponseEntity<>(room, HttpStatus.OK);

	}

	/**
	 * This method is used for deleting a room.
	 * 
	 * @param id This is the id of the deleted room.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@DeleteMapping("/rooms/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDTO> deleteRoom(@PathVariable("id") long id) {
		Room room = service.findOne(id);

		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		RoomDTO dto = new RoomDTO(room);
		service.remove(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping("/rooms/free")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDateDTO> getFreeRooms(@RequestBody String date) {
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date endDate = DateUtil.addMinutes(startDate, 30);

		List<Room> rooms = service.findAll();
		List<RoomDTO> dto = new ArrayList<>();

		for (Room r : rooms) {
			boolean free = true;
			for (Appointment a : r.getAppointments()) {
				if (DateUtil.overlap(startDate, endDate, a.getStartDate(), a.getEndDate()) == true) {
					free = false;
				}
			}
			if (free) {
				dto.add(new RoomDTO(r));
			}
		}
		RoomDateDTO roomDto = new RoomDateDTO(dto, endDate.getTime());
		return new ResponseEntity<>(roomDto, HttpStatus.OK);
	}

	@PostMapping("/rooms/terms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getTermsForRooms(@RequestBody String date) {

		if (date == null || "".equals(date))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = new ClinicAdministrator((ClinicAdministrator) a.getPrincipal());

		long dateMili = Long.parseLong(date);
		Date startDate = new Date(dateMili);

		List<Date> allDays = DateUtil.getSevenDays(startDate);

		Clinic clinic = admin.getClinic();
		// getovati sve sobe iz te klinike
		List<Room> rooms = service.findByClinic(clinic);
		// napravi mapu -> key je dan, value je objekat koji ima sobu i prvi slobodan
		// termin
		List<RoomTermDTO> roomInfo = new ArrayList<>();
		for (Room r : rooms) {
			for (Date d : allDays) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cal.set(Calendar.HOUR_OF_DAY, 18);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date endDate = cal.getTime();
				List<Appointment> apps = appService.findByRoomAndDate(r, d, endDate);
				// svi termini u jednom danu
				List<Date> allTermsInDay = getAllTerms(d);
				// pronadji prvi slobodan termin za tu sobu tog dana
				List<Date> clearTerms = removeOverlap(allTermsInDay, apps);
				Date firstTerm = clearTerms.isEmpty() ? clearTerms.get(0) : null;
				if (firstTerm != null) {
					roomInfo.add(new RoomTermDTO(r, firstTerm));
					break;
				}
			}
		}
		
		Collections.sort(roomInfo);
		return new ResponseEntity<>(roomInfo, HttpStatus.OK);
	}
	

	private List<Date> removeOverlap(List<Date> dates, List<Appointment> apps) {
		Calendar startApp = Calendar.getInstance();
		Calendar endApp = Calendar.getInstance();
		Calendar startTerm = Calendar.getInstance();
		for (Appointment a : apps) {
			startApp.setTime(a.getStartDate());
			endApp.setTime(a.getEndDate());
			for (Date d : dates) {
				startTerm.setTime(d);
				// pretpostavka - uvek je ista duzina pregleda i pocetak je u isto vreme
				if (startApp.get(Calendar.HOUR_OF_DAY) == startTerm.get(Calendar.HOUR_OF_DAY)
						&& startApp.get(Calendar.MINUTE) == startTerm.get(Calendar.MINUTE)) {
					dates.remove(d);
					break;
				}
			}
		}
		return dates;

	}

	public static List<Date> getAllTerms(Date day) {
		
		//TODO OBAVEZNO IZMENITI

		//int weekdayWorkStart = 8;
		int weekdayWorkEnd = 18;
		int weekdayBreakStart = 11;

		//int weekendWorkStart = 8;
		int weekendWorkEnd = 13;
		int weekendBreakStart = 11;

		int breakDurationHours = 1;

		int termsPerHour = 2;

		List<Date> data = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return data;
		}
		int workStart; 
		int workEnd; 
		int breakStart;
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			workStart = cal.get(Calendar.HOUR_OF_DAY);
			workEnd = weekendWorkEnd;
			breakStart = weekendBreakStart;
		} else {
			workStart = cal.get(Calendar.HOUR_OF_DAY);
			workEnd = weekdayWorkEnd;
			breakStart = weekdayBreakStart;
		}
		int currentTime = workStart;
		cal.set(Calendar.HOUR_OF_DAY, currentTime);
		cal.set(Calendar.MINUTE, 0);
		int termDuration = 60 / termsPerHour;
		// TODO: maybe change so term duration is flexible?
		while (currentTime != workEnd) {
			if (currentTime == breakStart) {
				currentTime += breakDurationHours;
				cal.add(Calendar.HOUR_OF_DAY, breakDurationHours);
				continue;
			}
			for (int i = 0; i != termsPerHour; i++) {
				Date newDate = cal.getTime();
				data.add(newDate);
				cal.add(Calendar.MINUTE, termDuration);
			}
			currentTime++;
		}
		return data;
	}
	
	boolean isEmptyOrNull(RoomDTO r) {
		if(r == null)
			return true;
		return ((r.getName() == null) || ("".equals(r.getName())));
	}

}
