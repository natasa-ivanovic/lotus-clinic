package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
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

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.security.CustomUserDetailsService;
import isamrs.tim17.lotus.service.AppointmentPriceService;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AuthorityService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.util.DateUtil;

@RestController
@RequestMapping("/api")
public class DoctorController {
	@Autowired
	private DoctorService service;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AppointmentPriceService appPriceService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	/**
	 * This method is used for adding a doctor.
	 * 
	 * @param doctor This is a doctor object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PostMapping("/doctors/{speciality}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addDoctor(@RequestBody UserDTO doctor, @PathVariable("speciality") long speciality) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();

		if (doctor == null || doctor.isEmpty())
			return new ResponseEntity<>("Some fields are still empty", HttpStatus.BAD_REQUEST);

		if (service.alreadyExistsUsername(doctor.getUsername()))
			return new ResponseEntity<>("That username already exists!", HttpStatus.BAD_REQUEST);

		AppointmentPrice spec = appPriceService.findOne(speciality);
		if (spec == null)
			return new ResponseEntity<>("Speciality not filled in", HttpStatus.BAD_REQUEST);

		Clinic clinic = clinicService.findOne(admin.getClinic().getId());
		if (clinic == null)
			return new ResponseEntity<>("Admin's clinic is null!", HttpStatus.BAD_REQUEST);

		Doctor d = new Doctor(doctor);
		ArrayList<Authority> auth = new ArrayList<>();
		auth.add(authorityService.findByName("ROLE_DOCTOR"));
		d.setAuthorities(auth);
		d.setClinic(clinic);
		d.setSpecialty(spec);
		d.setPassword(userDetailsService.encodePassword(doctor.getPassword()));
		d.setEnabled(true);
		d.setLastPasswordResetDate(null);
		service.save(d);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * This method is used for getting a list of doctors.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of doctors and the HTTP status
	 *         code.
	 */
	@GetMapping("/doctors")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllDoctors() {
		List<Doctor> doctors = service.findAll();

		// convert doctors to DTOs
		List<UserDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new UserDTO(d));
		}

		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a doctor.
	 * 
	 * @param id This is requested doctor's id.
	 * @return Doctor This returns the requested doctor.
	 */
	@GetMapping("/doctors/{id}")
	public ResponseEntity<Doctor> getDoctor(@PathVariable("id") long id) {
		Doctor doctor = service.findOne(id);
		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(doctor, HttpStatus.OK);

	}

	/**
	 * This method is used so doctors can get themselves.
	 * 
	 * @param None
	 * @return Doctor This returns the requested doctor.
	 */
	@GetMapping("/doctors/self")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<UserDTO> getYourself() {
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			UserDTO patient = new UserDTO((Doctor) a.getPrincipal());
			return new ResponseEntity<>(patient, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is used for editing your profile.
	 * 
	 * @param doc This is a doctor object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/doctors/self")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> updateYourself(@RequestBody UserDTO doc) {
		Doctor d = service.findOne(doc.getId());
		if (d == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		d.setName(doc.getName());
		d.setSurname(doc.getSurname());
		d.setBirthDate(doc.getBirthDate());
		d.setGender(doc.getGender());
		d.setAddress(doc.getAddress());
		d.setCity(doc.getCity());
		d.setCountry(doc.getCountry());
		d.setPhoneNumber(doc.getPhoneNumber());
		service.save(d);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * This method is used for editing a doctor.
	 * 
	 * @param doctor This is a doctor object from the HTTP request.
	 * @param id     This is the id of the edited doctor.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/doctors/{id}")
	public ResponseEntity<UserDTO> updateDoctor(@RequestBody UserDTO newDoctor, @PathVariable("id") Long id) {

		if (!id.equals(newDoctor.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// a doctor must exist
		Doctor doctor = service.findOne(newDoctor.getId());

		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		doctor.setName(newDoctor.getName());
		doctor.setSurname(newDoctor.getSurname());
		doctor.setUsername(newDoctor.getUsername());
		doctor.setBirthDate(newDoctor.getBirthDate());
		doctor.setGender(newDoctor.getGender());
		doctor.setAddress(newDoctor.getAddress());
		doctor.setCity(newDoctor.getCity());
		doctor.setCountry(newDoctor.getCountry());
		doctor.setPhoneNumber(newDoctor.getPhoneNumber());

		doctor = service.save(doctor);
		return new ResponseEntity<>(new UserDTO(doctor), HttpStatus.OK);

	}

	/**
	 * This method is used for deleting a doctor.
	 * 
	 * @param id This is the id of the deleted doctor.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<Object> deleteDoctor(@PathVariable("id") long id) {

		Doctor doctor = service.findOne(id);

		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		service.remove(id);
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
	
	@PostMapping("/doctors/newAppointment")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<Date>> getFreeTerms(@RequestBody String date) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		
		long dateMili = Long.parseLong(date);
		Date startDate = new Date(dateMili);
		Date endDate = DateUtil.endOfDay(startDate);
		List<Date> terms = DateUtil.getAllTerms(startDate);
		List<Appointment> appointments = appointmentService.findByDate(doctor, startDate, endDate);
		terms = DateUtil.removeOverlap(terms, appointments);
		
		return new ResponseEntity<>(terms, HttpStatus.OK);
		
		
	}

}
