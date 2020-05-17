package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.RegistrationRequestDTO;
import isamrs.tim17.lotus.dto.RoomRequestDTO;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MailSenderModel;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
	
	@Autowired private RequestService service;
	@Autowired public MailSenderModel mailSender;
	@Autowired public PatientService patientService;
	@Autowired public DoctorService doctorService;
	@Autowired public AppointmentTypeService appTypeService;
	
	@GetMapping("/registrations")
	public ResponseEntity<List<RegistrationRequestDTO>> getRegistrations() {
		List<RegistrationRequest> li = service.findRegistrations();
		List<RegistrationRequestDTO> liDTO = new ArrayList<>();
		for(RegistrationRequest rq: li) {
			liDTO.add(new RegistrationRequestDTO(rq));
		}
		return new ResponseEntity<List<RegistrationRequestDTO>>(liDTO, HttpStatus.OK);
	}
	
	@GetMapping("/rooms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getRoomRequests() {
		List<RoomRequest> rr = service.getRoomRequests();
		List<Object> requests = new ArrayList<>();
		
		for (RoomRequest r : rr) {
			Patient patient = patientService.findOne(r.getPatient());
			Doctor doctor = doctorService.findOne(r.getDoctor());
			Date startDate = r.getDate();
			RoomRequestDTO dto = new RoomRequestDTO(startDate, patient, doctor);
			requests.add(dto);
		}
		
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/registrations/auth/{id}")
	public ResponseEntity<RegistrationRequestDTO> authenticateRegistration(@PathVariable("id") long id) {
		Request req = service.findOne(id);
		RegistrationRequest rgReq = (RegistrationRequest)req;
		req.setStatus(RequestStatus.APPROVED);
		
		
		//random string generation
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 30;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
		
	    String content = "Hello\nWe at lotus clinic have reviewed your registration request and decided its valid.\nPlease follow this link to activate you account:\n"
	    		+ "http://localhost:8080/registrations/" + generatedString;
		mailSender.sendMsg(rgReq.getPatient().getUsername(), "Account registration", content);
		rgReq.setKey(generatedString);
		service.save(req);
		return new ResponseEntity<RegistrationRequestDTO>(new RegistrationRequestDTO(rgReq), HttpStatus.OK);
	}
	

	@GetMapping("/registrations/{key}")
	public ResponseEntity<RegistrationRequestDTO> registerUser(@PathVariable("key") String key) {
		Request req = null;
		try {
			req = service.findOneByKey(key);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		RegistrationRequest rgReq = (RegistrationRequest) req;
		rgReq.getPatient().setEnabled(true);
		service.save(req);
		return new ResponseEntity<>(new RegistrationRequestDTO(rgReq), HttpStatus.OK);
	}
}
