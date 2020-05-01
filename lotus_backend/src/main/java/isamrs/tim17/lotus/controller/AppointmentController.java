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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.PremadeAppDTO;
import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.service.AppointmentService;

@RestController
@RequestMapping("/api")
public class AppointmentController {
	
	@Autowired
	private AppointmentService service;
	
	@GetMapping("/appointments")
	//@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR'")
	public ResponseEntity<List<Appointment>> getAppointments() {
		
		List<Appointment> appointments = service.findAll();
		return new ResponseEntity<>(appointments, HttpStatus.OK);
		
	}
	
	@GetMapping("/appointments/{id}")
	//@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR'")
	public ResponseEntity<Appointment> getAppointment(@PathVariable("id") long id) {
		Appointment app = service.findOne(id);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(app, HttpStatus.OK);
	}
	
	@GetMapping("/appointments/premade")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PremadeAppDTO>> getPremadeApps() {
		List<Appointment> app = service.findByStatus(AppointmentStatus.PREMADE);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		List<PremadeAppDTO> dto = new ArrayList<PremadeAppDTO>();
		for (Appointment a : app) {
			dto.add(new PremadeAppDTO(a));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/appointments/schedule/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> scheduleApp(@PathVariable("id") long id) {
		Appointment app = service.findOne(id);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		app.setStatus(AppointmentStatus.SCHEDULED);
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		app.setMedicalRecord(patient.getMedicalRecord());
		service.save(app);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	@PostMapping("/appointments")
	public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment app) {
		//if (isEmptyOrNull(app)) {
			//System.out.println("Error in appointment!");
			//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		//}
		service.save(app);
		return new ResponseEntity<>(app, HttpStatus.OK);
	}
	
	/*private boolean isEmptyOrNull(Appointment app) {
		if (app == null)
			return true;
		if (app.getStartDate() == null || "".equals(app.getStartDate().toString()))
			return true;
		if (app.getEndDate() == null || "".equals(app.getEndDate().toString()))
			return true;
		
			
	}*/
	
	
}
