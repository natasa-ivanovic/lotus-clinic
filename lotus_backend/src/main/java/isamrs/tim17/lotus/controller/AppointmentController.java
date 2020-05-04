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

import isamrs.tim17.lotus.dto.AppointmentDTO;
import isamrs.tim17.lotus.dto.PremadeAppDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.service.PatientService;

@RestController
@RequestMapping("/api")
public class AppointmentController {
	
	@Autowired private AppointmentService service;
	@Autowired private RoomService roomService;
	@Autowired private AppointmentTypeService appointmentTypeService;
	@Autowired private DoctorService doctorService;
	
	
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
	
	@GetMapping("/appointments/patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PremadeAppDTO>> getMyAppointments() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		
		List<Appointment> apps = service.findByMedicalRecord(patient.getMedicalRecord());
		if (apps == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		List<PremadeAppDTO> dto = new ArrayList<PremadeAppDTO>();
		for (Appointment app : apps) {
			if (app.getStatus().equals(AppointmentStatus.SCHEDULED))
				dto.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/appointments/doctor")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getDoctorAppointments() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		
		List<Appointment> apps = service.findByDoctor(doctor);
		if (apps == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		List<PremadeAppDTO> dto = new ArrayList<PremadeAppDTO>();
		for (Appointment app : apps) {
			if(app.getStatus() == AppointmentStatus.PREMADE || app.getStatus() == AppointmentStatus.CANCELED)
				continue;
			PremadeAppDTO newDTO = new PremadeAppDTO(app);
			newDTO.setPatientName(app.getMedicalRecord().getPatient().getName());
			dto.add(newDTO);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/appointments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentDTO app) {
		//VALIDACIJA
		
		System.out.println(app);
		SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = null;
		Date end = new Date(app.getEndDateLong());
		try {
			start = sdfStart.parse(app.getStartDateString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Room room = roomService.findOne(app.getRoom());
		System.out.println(room);
		
		Doctor doc = doctorService.findOne(app.getDoctor());
		System.out.println(doc);
		
		AppointmentType at = appointmentTypeService.findOne(app.getAppointmentType());
		System.out.println(at);
		
		
		Appointment newApp = new Appointment(start, end, at, doc, room);
		service.save(newApp);
		return new ResponseEntity<>(HttpStatus.OK);
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
