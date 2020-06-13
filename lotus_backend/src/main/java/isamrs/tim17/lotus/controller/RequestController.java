package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import isamrs.tim17.lotus.model.MailSenderModel;
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
import isamrs.tim17.lotus.service.MedicalRecordService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.util.RandomUtil;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

	@Autowired
	private RequestService service;
	@Autowired
	public MailSenderModel mailSender;
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

		// getuj samo rikvestove za adminovu kliniku
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		long clinicId = admin.getClinic().getId();

		List<RoomRequest> rr = service.getRoomRequests();
		List<Object> requests = new ArrayList<>();

		for (RoomRequest r : rr) {
			List<Doctor> doctors = getDoctors(r);
			List<Doctor> clinicDoctors = new ArrayList<>();
			Patient patient = patientService.findOne(r.getPatient());
			Date startDate = r.getDate();
			for (Doctor d : doctors) {
				if (d.getClinic().getId() == clinicId) {
					clinicDoctors.add(d);
				}
			}
			RoomRequestDTO dto = null;
			if (clinicDoctors.size() == 1)
				dto = new RoomRequestDTO(r.getId(), startDate, patient, clinicDoctors.get(0));
			else
				dto = new RoomRequestDTO(r.getId(), startDate, patient, clinicDoctors, r.getAppType().getName());
			requests.add(dto);
		}
		return new ResponseEntity<>(requests, HttpStatus.OK);
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
			roomRequest = new RoomRequest(request.getStartDate(), user.getId(), doctors, RoomRequestType.PATIENT_APP,
					doc.getSpecialty().getPrice(), doc.getSpecialty().getType());
		} else if (user.getRole().equals("DOCTOR") && user.getId() == doc.getId()) {
			patient = doc.getName() + " " + doc.getSurname();
			roomRequest = new RoomRequest(request.getStartDate(), request.getPatient().getId(), doctors,
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
		RoomRequest roomRequest = new RoomRequest(cal.getTime(), request.getPatient().getId(), doctors,
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
		req.setStatus(RequestStatus.APPROVED);
		req.setAdmin(admin);

		String generatedString = RandomUtil.buildAuthString();

		String content = "Hello\nWe at Lotus Clinic have reviewed your registration request and decided it is valid.\nPlease follow this link to activate your account:\n"
				+ "https://lotus-clinic.herokuapp.com/registrations/" + generatedString + " \nLotus Clinic Staff";
		mailSender.sendMsg(rgReq.getPatient().getUsername(), "Account registration", content);
		rgReq.setKey(generatedString);
		service.save(req);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/registrations/decline/{id}")
	public ResponseEntity<Object> declineRegistration(@PathVariable("id") long id, @RequestBody String message) {
		Request req = service.findOne(id);
		RegistrationRequest rgReq = (RegistrationRequest) req;
		req.setStatus(RequestStatus.REJECTED);

		String content = "Hello\nWe at Lotus Clinic have reviewed your registration request and decided it is invalid.\nThe reason your request was declined is:\n"
				+ message + "\nWe hope we may be of service another time.\nLotus Clinic Staff";
		mailSender.sendMsg(rgReq.getPatient().getUsername(), "Account registration", content);
		service.save(req);
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

	private List<Doctor> getDoctors(RoomRequest r) {
		Set<Doctor> docs = r.getDoctors();
		List<Doctor> doctors = new ArrayList<>();

		Iterator<Doctor> it = docs.iterator();
		while (it.hasNext()) {
			Doctor d = doctorService.findOne(it.next().getId());
			doctors.add(d);
		}

		return doctors;
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
