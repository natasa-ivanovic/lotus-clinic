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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.CalendarEntryDTO;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.CalendarEntryService;

@RestController
@RequestMapping("/api/calendarentries")
public class CalendarEntryController {
	
	@Autowired
	private CalendarEntryService service;
	
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

}
