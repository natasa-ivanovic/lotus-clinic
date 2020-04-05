package isamrs.tim17.lotus.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.model.Doctor;

@RestController
public class DoctorController {
	
	public static HashMap<Integer, Doctor> doctors;
	
	@PostMapping("/doctors")
	public ResponseEntity<Object> addDoctor(@RequestBody Doctor doctor) {
		
		int id = doctor.getId();
		if (doctors.containsKey(id))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		doctors.put(id, doctor);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<Object> getDoctors() {
		if (doctors == null)
			doctors();
		return new ResponseEntity<>(doctors.values(), HttpStatus.OK);
	}
	
	@GetMapping("/doctors/{id}")
	public Doctor getDoctor(@PathVariable("id") int id) {
		return doctors.get(id);	
	}
	
	@PutMapping("/doctors/{id}")
	public ResponseEntity<Object> editDoctor(@RequestBody Doctor doctor, @PathVariable("id") int id) {
		
		int editedId = doctor.getId();
		if (!doctors.containsKey(id))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		doctors.remove(id);
		doctors.put(editedId, doctor);
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	
	private void doctors() {
		doctors = new HashMap<Integer, Doctor>();
		Doctor d1 = new Doctor("doc1@gmail.com", "sifra1", "Mario", "Kujundzic", "Fruskogorska", "NS", "RS", "1234", 1);
		Doctor d2 = new Doctor("doc2@gmail.com", "sifra2", "Natasa", "Ivanovic", "Bulevar Oslobodjenja", "NS", "RS", "1235", 2);
		Doctor d3 = new Doctor("doc3@gmail.com", "sifra3", "Bela", "Vajda", "Adice", "NS", "RS", "1236", 3);
		doctors.put(d1.getId(), d1);
		doctors.put(d2.getId(), d2);
		doctors.put(d3.getId(), d3);
		
		
	}
	
	
	

}
