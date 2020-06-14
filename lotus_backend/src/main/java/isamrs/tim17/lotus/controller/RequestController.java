package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import isamrs.tim17.lotus.dto.RegistrationRequestDTO;
import isamrs.tim17.lotus.dto.RoomRequestDTO;
import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.ClinicalCentreAdministrator;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.RoomRequestType;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.AppointmentPriceService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.MailSenderService;
import isamrs.tim17.lotus.service.MedicalRecordService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.UserService;
import isamrs.tim17.lotus.util.RandomUtil;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

	@Autowired
	private RequestService service;
	@Autowired
	public MailSenderService mailSender;
	@Autowired
	public PatientService patientService;
	@Autowired
	public DoctorService doctorService;
	@Autowired
	public AppointmentTypeService appTypeService;
	@Autowired
	public AppointmentPriceService appPriceService;
	@Autowired
	public MedicalRecordService medicalService;
	@Autowired	
	public UserService userService;

	@GetMapping("/registrations")
	public ResponseEntity<List<RegistrationRequestDTO>> getRegistrations() {
		List<RegistrationRequest> li = service.findRegistrations();
		List<RegistrationRequestDTO> liDTO = new ArrayList<>();
		for (RegistrationRequest rq : li) {
			liDTO.add(new RegistrationRequestDTO(rq));
		}
		return new ResponseEntity<>(liDTO, HttpStatus.OK);
	}

	@GetMapping("/rooms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getRoomRequests() {

		List<RoomRequest> requests = service.getRoomRequests();
		List<RoomRequestDTO> requsetDTOs = new ArrayList<>();
		for(RoomRequest req : requests) {
			requsetDTOs.add(new RoomRequestDTO(req));
		}
		return new ResponseEntity<>(requsetDTOs, HttpStatus.OK);
	}

	@PostMapping("/appointment")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<Object> requestAppointment(@RequestBody RoomRequestDTO request) {

		if (request.getStartDate().before(new Date()) || request.getStartDate() == null || request.getDoctors() == null
				|| !checkDoctorsId(request.getDoctors()))
			return new ResponseEntity<>("Error in request for room! All fields must be populated.",
					HttpStatus.BAD_REQUEST);

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		Set<Doctor> doctors = new HashSet<>();
		Doctor doc = doctorService.findOne(request.getDoctors().get(0).getId());
		RoomRequest checkRequest = service.findByStartDateAndDoctor(request.getStartDate(), doc);
		if (checkRequest != null)
			return new ResponseEntity<>("Someone already requested the selected doctor in selected term!",
					HttpStatus.BAD_REQUEST);

		doctors.add(doc);
		RoomRequest roomRequest = null;
		String patient = null;
		if (user.getRole().equals("PATIENT")) {
			patient = user.getName() + " " + user.getSurname();
			roomRequest = new RoomRequest(request.getStartDate(), (Patient)user, doctors, RoomRequestType.PATIENT_APP,
					doc.getSpecialty().getPrice(), doc.getSpecialty().getType());
		} else if (user.getRole().equals("DOCTOR") && user.getId() == doc.getId()) {
			patient = doc.getName() + " " + doc.getSurname();
			Patient pata = patientService.findOne(request.getPatient().getId());
			roomRequest = new RoomRequest(request.getStartDate(),pata, doctors,
					RoomRequestType.DOCTOR_APP, doc.getSpecialty().getPrice(), doc.getSpecialty().getType());
		} else
			return new ResponseEntity<>("Cannot request an appointment for another doctor!", HttpStatus.BAD_REQUEST);
		roomRequest.setStatus(RequestStatus.PENDING);
		try {
			service.save(roomRequest, doc.getId());
		} catch (Exception e) {
			return new ResponseEntity<>("Someone already requested the selected doctor in selected term!",
					HttpStatus.BAD_REQUEST);
		}
		Clinic c = doc.getClinic();
		for (ClinicAdministrator admin : c.getClinicAdministrators()) {
			mailSender.sendAdminNotificationAppointment(admin.getUsername(), patient,
					doc.getSpecialty().getType().getName(), doc.getName() + " " + doc.getSurname());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/operation")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> requestOperation(@RequestBody RoomRequestDTO request) {
		if (request.getStartDate().before(new Date()) || request.getStartDate() == null || request.getDoctors() == null
				|| !checkDoctorsId(request.getDoctors()))
			return new ResponseEntity<>("Error in request for room! All fields must be populated.",
					HttpStatus.BAD_REQUEST);

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		long appId = 0;
		try {
			appId = Long.parseLong(request.getType());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AppointmentPrice type = appPriceService.findOne(appId);
		Set<Doctor> doctors = convertDocsToSet(request.getDoctors());
		Calendar cal = Calendar.getInstance();
		cal.setTime(request.getStartDate());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		Patient patient = patientService.findOne(request.getPatient().getId());
		RoomRequest roomRequest = new RoomRequest(cal.getTime(), patient, doctors,
				RoomRequestType.DOCTOR_OPER, type.getPrice(), type.getType());
		roomRequest.setStatus(RequestStatus.PENDING);
		roomRequest.setClinic(doctor.getClinic());
		service.save(roomRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/registrations/auth/{id}")
	public ResponseEntity<Object> authenticateRegistration(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicalCentreAdministrator admin = (ClinicalCentreAdministrator) a.getPrincipal();
		Request req = service.findOne(id);
		RegistrationRequest rgReq = (RegistrationRequest) req;
		rgReq.setStatus(RequestStatus.APPROVED);
		rgReq.setAdmin(admin);

		String generatedString = RandomUtil.buildAuthString();

		rgReq.setKey(generatedString);
		service.save(rgReq);
		
		mailSender.sendPatientConfirmRegistration(rgReq.getPatient().getUsername(), generatedString);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/registrations/decline/{id}")
	public ResponseEntity<Object> declineRegistration(@PathVariable("id") long id, @RequestBody String message) {
		Request req = service.findOne(id);
		RegistrationRequest rgReq = (RegistrationRequest) req;
		req.setStatus(RequestStatus.REJECTED);

		service.save(req);
		userService.remove(rgReq.getPatient().getId());
		mailSender.sendPatientInvalidRegistration(rgReq.getPatient().getUsername(), message);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/registrations/{key}")
	public ResponseEntity<RegistrationRequestDTO> registerUser(@PathVariable("key") String key) {
		Request req = null;
		try {
			req = service.findOneByKey(key);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		RegistrationRequest rgReq = (RegistrationRequest) req;
		rgReq.getPatient().setEnabled(true);
		service.save(req);
		MedicalRecord mr = new MedicalRecord(170, 90, new HashSet<>(), "", rgReq.getPatient());
		medicalService.save(mr);

		return new ResponseEntity<>(new RegistrationRequestDTO(rgReq), HttpStatus.OK);
	}

	private boolean checkDoctorsId(List<UserDTO> doctors) {
		for (UserDTO doc : doctors) {
			if (doc.getId() == 0)
				return false;
		}
		return true;
	}

	private Set<Doctor> convertDocsToSet(List<UserDTO> docs) {
		Set<Doctor> doctors = new HashSet<>();
		for (UserDTO doctor : docs) {
			Doctor d = doctorService.findOne(doctor.getId());
			doctors.add(d);
		}
		return doctors;
	}
}
