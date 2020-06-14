package isamrs.tim17.lotus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.VacationRequestDTO;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Nurse;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.model.Vacation;
import isamrs.tim17.lotus.model.VacationRequest;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.MailSenderService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.VacationService;

@RestController
@RequestMapping("/api/vacation")
public class VacationRequestController {
	
	@Autowired RequestService service;
	@Autowired VacationService vacationService;
	@Autowired CalendarEntryService calendarEntryService;
	@Autowired MailSenderService mailSender;

	@PostMapping("")
	@PreAuthorize("hasAnyRole('NURSE', 'DOCTOR')")
	public ResponseEntity<Object> addRequest(@RequestBody Dates dates) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dates.startDate);
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dates.endDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error in date format!", HttpStatus.BAD_REQUEST);
		}
		Date today = new Date();
		if (startDate.before(today) || endDate.before(today) || endDate.before(startDate)) {
			return new ResponseEntity<>("Error in date format!", HttpStatus.BAD_REQUEST);
		}
		
		VacationRequest request = new VacationRequest();
		
		if (user.getRole().equals("NURSE")) {
			Nurse nurse = (Nurse) user;
			request.setStartDate(startDate);
			request.setEndDate(endDate);
			request.setClinic(nurse.getClinic());
			request.setStatus(RequestStatus.PENDING);
			request.setMedicalPerson(nurse);
			
		} else {
			Doctor doctor = (Doctor) user;
			// provera da li trazi godisnji, a ima obaveze
			List<CalendarEntry> work = calendarEntryService.findByMedicalPersonAndDate(user, startDate, endDate);
			if (!work.isEmpty())
				return new ResponseEntity<>("You have scheduled appointments and/or operations in this period of time!", HttpStatus.BAD_REQUEST);
			request.setStartDate(startDate);
			request.setEndDate(endDate);
			request.setClinic(doctor.getClinic());
			request.setStatus(RequestStatus.PENDING);
			request.setMedicalPerson(doctor);
			
		}
		
		
		service.save(request);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<VacationRequestDTO>> getVacationRequests() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		List<VacationRequest> vacationList = service.getVacationRequests(admin.getClinic(), RequestStatus.PENDING);
		List<VacationRequestDTO> vacationDTOList = new ArrayList<>();
		for(VacationRequest req : vacationList) {
			vacationDTOList.add(new VacationRequestDTO(req));
		}
		return new ResponseEntity<>(vacationDTOList, HttpStatus.OK);
	}
	
	@PostMapping("/auth/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> setVacation(@PathVariable("id") long id) {
		Request req = service.findOne(id);
		req.setStatus(RequestStatus.APPROVED);
		service.save(req);
		VacationRequest vacationRequest = (VacationRequest) req;
		Vacation vacation = new Vacation(vacationRequest);
		mailSender.sendVacationAccept(vacationRequest, vacationRequest.getMedicalPerson().getUsername());
		vacationService.save(vacation);
		CalendarEntry entry = new CalendarEntry(vacation);
		calendarEntryService.save(entry);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/decline/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> rejectVacation(@PathVariable("id") long id) {
		Request req = service.findOne(id);
		VacationRequest vacationreq = (VacationRequest) req;
		req.setStatus(RequestStatus.REJECTED);
		mailSender.sendVacationDecline(vacationreq, vacationreq.getMedicalPerson().getUsername());
		service.save(req);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

class Dates {
	public String startDate;
	public String endDate;
}