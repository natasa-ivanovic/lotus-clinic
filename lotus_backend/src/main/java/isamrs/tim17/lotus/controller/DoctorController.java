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

@RestController
public class DoctorController {
	
//	
//	public static HashMap<Integer, Doctor> doctors;
//	
//	/**
//	 * This method is used for adding a doctor.
//	 * @param doctor This is a doctor object from the HTTP request.
//	 * @return ResponseEntity This returns the HTTP status code.
//	 * */
//	@PostMapping("/doctors")
//	public ResponseEntity<Object> addDoctor(@RequestBody Doctor doctor) {
//		loadDoctors();
//		int id = doctor.getId();
//		if (doctors.containsKey(id))
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		
//		doctors.put(id, doctor);
//		return new ResponseEntity<>(HttpStatus.OK);	
//	}
//	
//	/**
//	 * This method is used for getting a list of doctors.
//	 * @param Nothing.
//	 * @return ResponseEntity This returns the list of doctors and the HTTP status code.
//	 * */
//	@GetMapping("/doctors")
//	public ResponseEntity<Object> getDoctors() {
//		loadDoctors();
//		return new ResponseEntity<>(doctors.values(), HttpStatus.OK);
//	}
//	
//	
//	/**
//	 * This method is used for getting a doctor.
//	 * @param id This is requested doctor's id.
//	 * @return Doctor This returns the requested doctor.
//	 * */
//	@GetMapping("/doctors/{id}")
//	public Doctor getDoctor(@PathVariable("id") int id) {
//		loadDoctors();
//		return doctors.get(id);
//		
//	}
//	
//	/**
//	 * This method is used for editing a doctor.
//	 * @param doctor This is a doctor object from the HTTP request.
//	 * @param id This is the id of the edited doctor.
//	 * @return ResponseEntity This returns the HTTP status code.
//	 * */
//	@PutMapping("/doctors/{id}")
//	public ResponseEntity<Object> editDoctor(@RequestBody Doctor doctor, @PathVariable("id") int id) {
//		loadDoctors();
//		int editedId = doctor.getId();
//		
//		if (!doctors.containsKey(id))
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		
//		doctors.remove(id);
//		doctors.put(editedId, doctor);
//		return new ResponseEntity<>(HttpStatus.OK);	
//	}
//	
//	/**
//	 * This method is used for deleting a doctor.
//	 * @param id This is the id of the deleted doctor.
//	 * @return ResponseEntity This returns the HTTP status code.
//	 * */
//	@DeleteMapping("/doctors/{id}")
//	public ResponseEntity<Object> deletePatient(@PathVariable("id") int id) {
//		loadDoctors();
//		doctors.remove(id);
//		return new ResponseEntity<>(HttpStatus.OK);	
//	}
//
//	/**
//	 * This method is used for loading doctors.
//	 * @param Nothing.
//	 * @return Nothing.
//	 * */
//	private void loadDoctors() {
//		if (doctors == null) {
//			doctors = new HashMap<Integer, Doctor>();
//			Doctor d1 = new Doctor("doc1@gmail.com", "sifra1", "Mario", "Kujundzic", "Fruskogorska", "NS", "RS", "1234", 1);
//			Doctor d2 = new Doctor("doc2@gmail.com", "sifra2", "Natasa", "Ivanovic", "Bulevar Oslobodjenja", "NS", "RS", "1235", 2);
//			Doctor d3 = new Doctor("doc3@gmail.com", "sifra3", "Bela", "Vajda", "Adice", "NS", "RS", "1236", 3);
//			doctors.put(d1.getId(), d1);
//			doctors.put(d2.getId(), d2);
//			doctors.put(d3.getId(), d3);
//		}
//			
//		
//	}
	
}
