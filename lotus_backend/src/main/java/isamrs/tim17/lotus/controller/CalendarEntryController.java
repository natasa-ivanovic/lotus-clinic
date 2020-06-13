package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.CalendarEntryDTO;
import isamrs.tim17.lotus.dto.PremadeAppDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.RoomService;

@RestController
@RequestMapping("/api/calendarentries")
public class CalendarEntryController {
	
	@Autowired
	private CalendarEntryService service;
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('NURSE', 'DOCTOR')")
	public ResponseEntity<List<CalendarEntryDTO>> getById() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		List<CalendarEntry> entries = service.findByMedicalPersonId(user);
		List<CalendarEntryDTO> entriesDTO = new ArrayList<>();
		for(CalendarEntry entry : entries) {
			entriesDTO.add(new CalendarEntryDTO(entry));
		}
		return new ResponseEntity<>(entriesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getByRoom(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator user = (ClinicAdministrator) a.getPrincipal();
		Room r = roomService.findOne(id);
		if (r.getClinic().getId() !=  user.getClinic().getId()) 
			return new ResponseEntity<>("Cannot view another clinic's rooms calendar entries!", HttpStatus.BAD_REQUEST);
		
		List<CalendarEntry> entries = service.findByRoom(r);
		List<CalendarEntryDTO> entriesDTO = new ArrayList<>();
		for(CalendarEntry entry : entries) {
			entriesDTO.add(new CalendarEntryDTO(entry));
		}
		return new ResponseEntity<>(entriesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/startAppointment/{id}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> getStartAppointment(@PathVariable("id") long id) {
		CalendarEntry ce = service.findOne(id);
		if (ce == null)
			return new ResponseEntity<>("Calendar entry not found!", HttpStatus.BAD_REQUEST);
		
		Appointment a = ce.getAppointment();

		if (a == null) {
			return new ResponseEntity<>("Calendar entry doesn't have appointment!", HttpStatus.BAD_REQUEST);
		}
		
		if (!a.getStatus().equals(AppointmentStatus.SCHEDULED)) {
			return new ResponseEntity<>("Unable to start appointment!", HttpStatus.BAD_REQUEST);
		} 
		
		PremadeAppDTO pa = new PremadeAppDTO(a);
		return new ResponseEntity<>(pa, HttpStatus.OK);
		
	}

}
