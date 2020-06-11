package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

import isamrs.tim17.lotus.dto.ClinicDTO;
import isamrs.tim17.lotus.dto.DoctorDTO;
import isamrs.tim17.lotus.dto.PatientDTO;
import isamrs.tim17.lotus.dto.PatientRequest;
import isamrs.tim17.lotus.dto.RatingDTO;
import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.DoctorReview;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.ClinicReviewService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.DoctorReviewService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.util.DateUtil;
import isamrs.tim17.lotus.util.RatingUtil;

@RestController
@RequestMapping("/api")
public class PatientController {
	@Autowired
	private PatientService service;
	
	@Autowired 
	private DoctorService doctorService;
	
	@Autowired 
	private ClinicService clinicService;
	
	@Autowired
	private AppointmentTypeService typeService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private DoctorReviewService docReviewService;

	@Autowired
	private ClinicReviewService clinicReviewService;
	
	@Autowired
	private CalendarEntryService calendarService;
	
	/**
	 * This method is used for getting the list of patients.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of patients and the HTTP status
	 *         code.
	 */
	@GetMapping("/patients")
	@PreAuthorize("hasAnyRole('NURSE', 'DOCTOR')")
	public ResponseEntity<List<UserDTO>> getPatients() {
		List<Patient> patients = service.findAll();
		List<UserDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
				patientsDTO.add(new UserDTO(p));
		}
		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used for getting all unauthorized patients.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of patients and the HTTP status
	 *         code.
	 */
	
	@GetMapping("/patients/requests")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getUnauthorizedPatients() {
		List<Patient> patients = service.findByStatus(false);
		List<UserDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
			patientsDTO.add(new UserDTO(p));
		}
		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a patient.
	 * 
	 * @param id This is requested patient's id.
	 * @return Patient This returns the requested patient.
	 */
	@GetMapping("/patients/{id}")
	@PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
	public ResponseEntity<Patient> getPatient(@PathVariable("id") long id) {
		Patient patient = service.findOne(id);
		if (patient == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
	
	/**
	 * This method is used so patients can get themselves.
	 * 
	 * @param None
	 * @return Patient This returns the requested patient.
	 */
	@GetMapping("/patients/self")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<PatientDTO> getYourself() {
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			PatientDTO patient = new PatientDTO((Patient) a.getPrincipal());
			return new ResponseEntity<>(patient, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This method is used for editing your profile.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @param id      This is the id of the edited patient.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/patients/self")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> updateYourself(@RequestBody UserDTO patient) {
		Patient p = service.findOne(patient.getId());
		if (p == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		p.setName(patient.getName());
		p.setSurname(patient.getSurname());
		p.setBirthDate(patient.getBirthDate());
		p.setGender(patient.getGender());
		p.setAddress(patient.getAddress());
		p.setCity(patient.getCity());
		p.setCountry(patient.getCountry());
		p.setPhoneNumber(patient.getPhoneNumber());
		service.save(p);
		return new ResponseEntity<>(HttpStatus.OK);
	}



	/**
	 * This method is used for editing a patient.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @param id      This is the id of the edited patient.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/patients/{id}")
	public ResponseEntity<Object> updatePatient(@RequestBody UserDTO patient, @PathVariable("id") Long id) {
		if (!id.equals(patient.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Patient p = service.findOne(id);
		if (p == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		p.setName(patient.getName());
		p.setSurname(patient.getSurname());
		p.setUsername(patient.getUsername());
		p.setBirthDate(patient.getBirthDate());
		p.setGender(patient.getGender());
		p.setAddress(patient.getAddress());
		p.setCity(patient.getCity());
		p.setCountry(patient.getCountry());
		p.setPhoneNumber(patient.getPhoneNumber());
		p = service.save(p);
		return new ResponseEntity<>(new UserDTO(p), HttpStatus.OK);
	}

	/**
	 * This method is used for deleting a patient.
	 * 
	 * @param id This is the id of the deleted patient.
	 * @return ResponseEntity This returns the HTTP status code along with the
	 *         current patients.
	 */
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") long id) {
		Patient p = service.findOne(id);
		if (p == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		service.remove(id);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	/**
	 * This method is used to return a list of free terms for doctors which can do a specific
	 * type of appointment. It returns either a list of doctors and their free terms
	 * or a list of clinics which a list of doctors and their free terms.
	 * 
	 * @param pr Patient request object which contains the requestDate, appointmentType and 
	 * a boolean which indicates whether the user wants to see clinics or just their doctors.
	 * @return ResponseEntity This returns the HTTP status code along the objects that were requested.
	 */
	@PostMapping("/patients/request")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> requestList(@RequestBody PatientRequest pr) {
		if (pr.getRequestDate() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Date date = new Date(pr.getRequestDate());
		AppointmentType type = typeService.findOne(pr.getAppointmentType());
		if (type == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			if (pr.isClinics()) {
				List<Clinic> clinics = clinicService.findAll();
				ListIterator<Clinic> it = clinics.listIterator();
				List<ClinicDTO> clinicList = new ArrayList<>();
				while (it.hasNext()) {
					Clinic c = it.next();
					if (c.getDoctors().isEmpty())
						it.remove();
					ClinicDTO dto = new ClinicDTO(c);
					Iterator<Doctor> docIt = c.getDoctors().iterator();
					while (docIt.hasNext()){
						Doctor d = docIt.next();
						if (d.getSpecialty().getType().getId() != type.getId()) {
							docIt.remove();						
							continue;
						}									
						List<Date> availableDates = DateUtil.getAllTerms(date, false);
						Date endDate = DateUtil.endOfDay(date);
						List<CalendarEntry> calendarEntries = calendarService.findByMedicalPersonAndDate(d, date, endDate);
						availableDates = DateUtil.removeOverlap(availableDates, calendarEntries);
						if (availableDates.isEmpty()) 
							docIt.remove();						
						 else {
							 List<DoctorReview> ratingList = docReviewService.findAllByDoctor(d);
							 double rating = RatingUtil.getAverageDoctorRating(ratingList);
							 dto.getDoctors().add(new DoctorDTO(d, rating, availableDates));
							 dto.setPrice(d.getSpecialty().getPrice());
						 }
					}
					if (c.getDoctors().isEmpty()) 
						it.remove();
					else {
						List<ClinicReview> reviews = clinicReviewService.findAllByClinic(c);
						double rating = RatingUtil.getAverageClinicRating(reviews);
						dto.setRating(rating);
						clinicList.add(dto);
					}
				}
				if (clinics.isEmpty())
					return new ResponseEntity<>("Empty list of clinics", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(clinicList, HttpStatus.OK);
			} else {
				List<Doctor> doctors = doctorService.findAll();
				List<DoctorDTO> results = new ArrayList<>();
				ListIterator<Doctor> it = doctors.listIterator();
				while (it.hasNext()) {
					Doctor d = it.next();
					if (d.getSpecialty().getType().getId() != type.getId()) {
						it.remove();
						continue;
					}
					List<Date> availableDates = DateUtil.getAllTerms(date, false);
					Date endDate = DateUtil.endOfDay(date);
					List<CalendarEntry> calendarEntries = calendarService.findByMedicalPersonAndDate(d, date, endDate);
					availableDates = DateUtil.removeOverlap(availableDates, calendarEntries);
					if (!availableDates.isEmpty()) {
						 List<DoctorReview> ratingList = docReviewService.findAllByDoctor(d);
						 double rating = RatingUtil.getAverageDoctorRating(ratingList);
						 results.add(new DoctorDTO(d, rating, availableDates));
					}
				}
				return new ResponseEntity<>(results, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Something bad happened!", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	/**
	 * This method is used to handle a rating for previous appointments.
	 * 
	 * @param rating RatingDTO object which contains all the necessary information for the doctor and clinic rating.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PostMapping("/patients/rate")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> sendRating(@RequestBody RatingDTO rating) {
		Appointment a = appointmentService.findOne(rating.getAppointmentId());
		if (a == null || rating.getClinicRating() == 0 || rating.getDoctorRating() == 0) 
			return new ResponseEntity<>("Ratings cannot be 0 or null!", HttpStatus.BAD_REQUEST);			
		if (a.isReviewed())
			return new ResponseEntity<>("Cannot rate same appointment multiple times!", HttpStatus.BAD_REQUEST);			
		try {
			appointmentService.saveRatings(a, rating);			
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot rate same appointment multiple times!", HttpStatus.BAD_REQUEST);			
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
