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
import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.RoomRequestType;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.util.DateUtil;

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
	private RequestService requestService;
	
	@Autowired
	private AppointmentService appointmentService;
	
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
		/*Authentication a = SecurityContextHolder.getContext().getAuthentication();
		long clinicId;
		try {
			Doctor loggedIn = (Doctor) a.getPrincipal();			
			clinicId = loggedIn.getClinic().getId();
		} catch(Exception e) {
			Nurse loggedIn = (Nurse) a.getPrincipal();		
			clinicId = loggedIn.getClinic().getId();			
		}*/
		List<Patient> patients = service.findAll();
		List<UserDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
			//if (p.getClinic().getId().equals(clinicId))
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
		p = service.save(p);
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
		if (id != patient.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Patient p = service.findOne(id);
		if (p == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		p.setName(patient.getName());
		p.setSurname(patient.getSurname());
		p.setUsername(patient.getEmail());
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

	@PostMapping("/patients/request")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> requestList(@RequestBody PatientRequest pr) {
		Date date = new Date(pr.getRequestDate());
		AppointmentType type = typeService.findOne(pr.getAppointmentType());
		try {
			if (pr.isClinics()) {
				// trebas vratiti listu klinika koja sadrzi barem jednog lekara koji moze da obavi pregled na taj dan
				// lista clinic DTO objekata koji imaju liste DTO od 1+ lekar DTO sa bitnim stvarima
				List<Clinic> clinics = clinicService.findAll();
				ListIterator<Clinic> it = clinics.listIterator();
				List<ClinicDTO> clinicList = new ArrayList<ClinicDTO>();
				while (it.hasNext()) {
					Clinic c = it.next();
					if (c.getDoctors().isEmpty())
						it.remove();
					ClinicDTO dto = new ClinicDTO(c);
					Iterator<Doctor> docIt = c.getDoctors().iterator();
					while (docIt.hasNext()){
						Doctor d = docIt.next();
						if (d.getSpecialty().getId() != type.getId()) {
							docIt.remove();						
							continue;
						}									
						List<Date> availableDates = DateUtil.getAllTerms(date);
						Date endDate = DateUtil.endOfDay(date);
						List<Appointment> appointments = appointmentService.findByDate(d, date, endDate);
						availableDates = DateUtil.removeOverlap(availableDates, appointments);
						if (availableDates.isEmpty()) 
							docIt.remove();						
						 else 
							dto.getDoctors().add(new DoctorDTO(d, 4.5, availableDates));
					}
					if (c.getDoctors().isEmpty()) 
						it.remove();
					else
						clinicList.add(dto);
				}
				if (clinics.isEmpty())
					return new ResponseEntity<>("Empty list of clinics", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(clinicList, HttpStatus.OK);
			} else {
				// trebas vratiti listu lekara koji mogu da obave pregled na taj dan, znaci nisu full 
				List<Doctor> doctors = doctorService.findAll();
				List<DoctorDTO> results = new ArrayList<DoctorDTO>();
				ListIterator<Doctor> it = doctors.listIterator();
				while (it.hasNext()) {
					Doctor d = it.next();
					if (d.getSpecialty().getId() != type.getId()) {
						it.remove();
						continue;
					}
					// TODO logika za proveru jel je free taj dan 
					// . . . 
					// get grade...
					// get all free dates...
					List<Date> availableTimes = new ArrayList<Date>();
					availableTimes.add(new Date(date.getTime()));
					results.add(new DoctorDTO(d, 4.5, availableTimes));
				}
				// potencijalno u jos jedan loop ali sumnjam da treba, ovo gore mozda u jedan loop isto
				return new ResponseEntity<>(results, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Something bad happened!", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@PostMapping("/patients/request/finish")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> requestAppointment(@RequestBody RoomRequest request) {
		if (request.getDate().equals(new Date()) || request.getDate().equals(null) || request.getDoctor() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		request.setPatient(patient.getId());
		request.setStatus(RequestStatus.PENDING);
		request.setType(RoomRequestType.PATIENT_APP);
		requestService.save(request);
		return new ResponseEntity<>(HttpStatus.OK);	
	}


}
