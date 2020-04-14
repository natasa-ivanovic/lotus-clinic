package isamrs.tim17.lotus.controller;

import java.util.Date;
import java.util.HashMap;

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

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Gender;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.UserType;


@RestController
@RequestMapping("/api")
public class PatientController {
	
	public static HashMap<Long, Patient> patients;
	
	/**
	 * This method is used for getting the list of patients.
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of patients and the HTTP status code.
	 * */
	@GetMapping("/patients")
	public ResponseEntity<Object> getPatients() {
		loadPatients();
		return new ResponseEntity<>(patients.values(), HttpStatus.OK);
	}
	
	/**
	 * This method is used for getting a patient.
	 * @param id This is requested patient's id.
	 * @return Patient This returns the requested patient.
	 * */
	@GetMapping("/patients/{id}")
	public Patient getPatient(@PathVariable("id") long id) {
		loadPatients();
		return patients.get(id);
	}
	
	/**
	 * This method is used for adding a patient.
	 * @param patient This is a patient object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code along with the current patients.
	 * */
	@PostMapping("/patients")
	public ResponseEntity<Object> addPatient(@RequestBody Patient patient) {
		loadPatients();
		if (patients.containsKey(patient.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		System.out.println(patient);
		patients.put(patient.getId(), patient);
		return new ResponseEntity<>(patients.values(), HttpStatus.OK);	
		
	}
	
	/**
	 * This method is used for editing a patient.
	 * @param patient This is a patient object from the HTTP request.
	 * @param id This is the id of the edited patient.
	 * @return ResponseEntity This returns the HTTP status code.
	 * */
	@PutMapping("/patients/{id}")
	public ResponseEntity<Object> editPatient(@RequestBody Patient patient, @PathVariable("id") long id) {
		loadPatients();
		patients.remove(id);
		patients.put(patient.getId(), patient);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	/**
	 * This method is used for deleting a patient.
	 * @param id This is the id of the deleted patient.
	 * @return ResponseEntity This returns the HTTP status code along with the current patients.
	 * */
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") long id) {
		loadPatients();
		patients.remove(id);
		return new ResponseEntity<>(patients.values(), HttpStatus.OK);	
	}
	
	
	/**
	 * This method is used for loading patients.
	 * @param Nothing.
	 * @return Nothing.
	 * */
	private void loadPatients() {
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
	}

}
