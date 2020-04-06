package isamrs.tim17.lotus.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.model.Patient;

@RestController
public class PatientController {
	
	public static HashMap<Integer, Patient> patients;
	
	@GetMapping("/patients")
	public ResponseEntity<Object> getPatients() {
		loadPatients();
		return new ResponseEntity<>(patients.values(), HttpStatus.OK);
	}
	
	@GetMapping("/patients/{id}")
	public Patient getPatient(@PathVariable("id") int id) {
		loadPatients();
		return patients.get(id);
	}
	
	@PostMapping("/patients")
	public Patient addPatient(@RequestBody Patient p) {
		loadPatients();
		System.out.println(p);
		return patients.put(p.getId(), p);
	}

	@PutMapping("/patients/{id}")
	public ResponseEntity<Object> editPatient(@RequestBody Patient p, @PathVariable("id") int id) {
		loadPatients();
		patients.remove(id);
		patients.put(p.getId(), p);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") int id) {
		loadPatients();
		patients.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	
	
	private void loadPatients() {
		if (patients == null) {
			patients = new HashMap<Integer, Patient>();
			Patient p1 = new Patient("mario@gmail.com", "mario", "Mario", "Kujundzic", "Jovana Jovanovic Zmaja 4", "Subotica", "Srbija", "066 126 215", 1);
			Patient p2 = new Patient("natasa@gmail.com", "nat", "Natasa", "Ivanovic", "Bulevara Oslobodjenja", "Novi Sad", "Srbija", "066 555 225", 2);
			Patient p3 = new Patient("crna@gmail.com", "beva", "Bela", "Vajda", "Neka adresa", "Novi Sad", "Srbija", "066 546 487", 3);
			patients.put(p1.getId(), p1);
			patients.put(p2.getId(), p2);
			patients.put(p3.getId(), p3);
		}
	}

}
