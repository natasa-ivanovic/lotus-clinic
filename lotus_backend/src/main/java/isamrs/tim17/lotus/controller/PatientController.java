package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Gender;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.UserType;
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
	public ResponseEntity<List<UserDTO>> getPatients() {
		List<Patient> patients = service.findAll();
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
	public ResponseEntity<Patient> getPatient(@PathVariable("id") int id) {
		Patient patient = service.findOne(id);
		if (patient == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}

	/**
	 * This method is used for adding a patient.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code along with the
	 *         current patients.
	 */
	@PostMapping("/patients")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
		if (isEmptyOrNull(patient)) {
			System.out.println("Error in patient!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		service.save(patient);
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}

	/**
	 * This method is used for editing a patient.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @param id      This is the id of the edited patient.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/patients/{id}")
	public ResponseEntity<Object> updatePatient(@RequestBody UserDTO patient, @PathVariable("id") long id) {
		if (id != patient.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Patient p = service.findOne(id);
		if (p == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		p.setName(patient.getName());
		p.setSurname(patient.getSurname());
		p.setEmail(patient.getEmail());
		p.setPassword(patient.getPassword());
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
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * This method is used for loading patients.
	 * 
	 * @param Nothing.
	 * @return Nothing.
	 */
	/*private void loadPatients() {
		if (patients == null) {
			Clinic c = new Clinic("Lotus", "Bulevar Oslobodjenja", "Covid19 klinika");
			patients = new HashMap<Long, Patient>();
			Patient p1 = new Patient("mario@gmail.com", "mario", "Mario", "Kujundzic", "Jovana Jovanovic Zmaja 4", "Subotica", "Srbija", "066 126 215", 1, UserType.patient, new Date(), Gender.male, c);
			Patient p2 = new Patient("natasa@gmail.com", "nat", "Natasa", "Ivanovic", "Bulevara Oslobodjenja", "Novi Sad", "Srbija", "066 555 225", 2, UserType.patient, new Date(), Gender.female, c);
			Patient p3 = new Patient("crna@gmail.com", "beva", "Bela", "Vajda", "Neka adresa", "Novi Sad", "Srbija", "066 546 487", 3, UserType.patient, new Date(), Gender.male, c);
			patients.put(p1.getId(), p1);
			patients.put(p2.getId(), p2);
			patients.put(p3.getId(), p3);
		}
	}*/
	
	private boolean isEmptyOrNull(Patient patient) {
		if (patient == null)
			return true;
		if (patient.getName() == null || "".equals(patient.getName()))
			return true;
		if (patient.getSurname() == null || "".equals(patient.getSurname()))
			return true;
		if (patient.getEmail() == null || "".equals(patient.getEmail()))
			return true;
		if (patient.getPassword() == null || "".equals(patient.getPassword()))
			return true;
		if (patient.getAddress() == null || "".equals(patient.getAddress()))
			return true;
		if (patient.getCity() == null || "".equals(patient.getCity()))
			return true;
		if (patient.getCountry() == null || "".equals(patient.getCountry()))
			return true;
		if (patient.getPhoneNumber() == null || "".equals(patient.getPhoneNumber()))
			return true;
		if (patient.getGender() == null || "".equals(patient.getGender().toString()))
			return true;
		if (patient.getBirthDate() == null || "".equals(patient.getBirthDate().toString()))
			return true;
		return false;
	}

}
