package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.RoomDTO;
import isamrs.tim17.lotus.dto.RoomDateDTO;
import isamrs.tim17.lotus.dto.RoomRequestDTO;
import isamrs.tim17.lotus.dto.RoomTermDTO;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.util.DateUtil;

@RestController
@RequestMapping("/api")
public class RoomController {

	@Autowired
	private RoomService service;
	@Autowired
	private CalendarEntryService calService;
	@Autowired
	private RequestService requestSevice;

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
	
	@GetMapping("/rooms/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getAllRoomsPage(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<Room> rooms = null;
		try {
			rooms = service.findByClinic(admin.getClinic(), paging);

		} catch (Exception e) {
			return new ResponseEntity<>("Error in forwarded arguments for sort!", HttpStatus.BAD_REQUEST);
		}
		if (rooms == null)
			return new ResponseEntity<>("Something went wrong. Please try again later.", HttpStatus.BAD_REQUEST);
		Page<RoomDTO> dto = rooms.map(new Function<Room, RoomDTO>() {
			@Override
			public RoomDTO apply(Room room) {
				return new RoomDTO(room);
			}
		});
		return new ResponseEntity<>(dto, HttpStatus.OK);

	
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

	@PostMapping("/rooms/free/{date}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getFreeRooms(@PathVariable Long date) {
		if (date == 0)
			return new ResponseEntity<>("Invalid date for room terms!", HttpStatus.BAD_REQUEST);
		Date startDate = new Date(date);

		Date endDate = DateUtil.addMinutes(startDate, 30);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) auth.getPrincipal();
		
		List<Room> rooms = service.findByClinic(admin.getClinic());
		List<RoomDTO> dto = new ArrayList<>();

		for (Room r : rooms) {
			CalendarEntry c = calService.findByDateAndRoom(r, startDate);
			if (c == null)
				dto.add(new RoomDTO(r));
		}
		RoomDateDTO roomDto = new RoomDateDTO(dto, endDate.getTime());
		return new ResponseEntity<>(roomDto, HttpStatus.OK);
	}

	@PostMapping("/rooms/terms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getTermsForRooms(@RequestBody RoomRequestDTO request) {
		return getTermsForRoomsAppointment(request);
	}
	
	
	public ResponseEntity<Object> getTermsForRoomsAppointment(RoomRequestDTO request) {

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		
		RoomRequest req = requestSevice.findRoomOneById(request.getId());
		Date startDate = request.getStartDate();

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
				cal.set(Calendar.HOUR_OF_DAY, 7);
				Date currentDate = cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, 18);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date endDate = cal.getTime();
				List<CalendarEntry> entries = calService.findByRoomAndDate(r, currentDate, endDate);
				//List<Appointment> apps = appService.findByRoomAndDate(r, d, endDate);
				// svi termini u jednom danu, pocevsi sa satom prosledjenog datuma
				List<Date> allTermsInDay = DateUtil.getAllTerms(d, true);
				// pronadji prvi slobodan termin za tu sobu tog dana
				List<Date> clearTerms = DateUtil.removeOverlap(allTermsInDay, entries);
				for(Doctor doctor : req.getDoctors()) {
					entries = calService.findByMedicalPersonAndDate(doctor, currentDate, endDate);
					clearTerms = DateUtil.removeOverlap(clearTerms, entries);
				}
				Date firstTerm = !clearTerms.isEmpty() ? clearTerms.get(0) : null;
				if (firstTerm != null) {
					roomInfo.add(new RoomTermDTO(r, firstTerm));
					break;
				}
			}
		}
		
		Collections.sort(roomInfo);
		return new ResponseEntity<>(roomInfo, HttpStatus.OK);
	}
		
	boolean isEmptyOrNull(RoomDTO r) {
		if(r == null)
			return true;
		return ((r.getName() == null) || ("".equals(r.getName())));
	}

}
