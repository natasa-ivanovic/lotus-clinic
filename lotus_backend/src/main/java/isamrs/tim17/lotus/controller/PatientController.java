package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
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
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.service.PatientService;

@RestController
@RequestMapping("/api")
public class PatientController {
	@Autowired
	private PatientService service;

	/**
	 * This method is used for getting the list of patients.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of patients and the HTTP status
	 *         code.
	 */
	@GetMapping("/patients")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
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
	public ResponseEntity<UserDTO> getYourself() {
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			UserDTO patient = new UserDTO((Patient) a.getPrincipal());
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



}
