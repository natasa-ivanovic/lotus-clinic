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

import isamrs.tim17.lotus.model.ClinicCentreAdmin;

@RestController
public class ClinicCentreAdminController {

	
	public static HashMap<Integer, ClinicCentreAdmin> clinicCentreAdmins;
	
	/**
	 * This method is used for adding a clinicCentreAdmin.
	 * @param clinicCentreAdmin This is a ClinicCentreAdmin object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 * */
	@PostMapping("/clinicCentreAdmins")
	public ResponseEntity<Object> addClinicCentreAdmin(@RequestBody ClinicCentreAdmin clinicCentreAdmin) {
		loadClinicCentreAdmins();
		int id = clinicCentreAdmin.getId();
		if (clinicCentreAdmins.containsKey(id))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		clinicCentreAdmins.put(id, clinicCentreAdmin);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	/**
	 * This method is used for getting a list of clinc centre admins.
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of clinic centre admins and the HTTP status code.
	 * */
	@GetMapping("/clinicCentreAdmins")
	public ResponseEntity<Object> getDoctors() {
		loadClinicCentreAdmins();
		return new ResponseEntity<>(clinicCentreAdmins.values(), HttpStatus.OK);
	}
	
	
	/**
	 * This method is used for getting a clinic centre admin.
	 * @param id This is requested clinic centre admin's id.
	 * @return ClinCentreAdmin This returns the requested clinic centre admin.
	 * */
	@GetMapping("/clinicCentreAdmins/{id}")
	public ResponseEntity<Object> getDoctor(@PathVariable("id") int id) {
		loadClinicCentreAdmins();
		return new ResponseEntity<>(clinicCentreAdmins.get(id), HttpStatus.OK);
		
	}
	
	/**
	 * This method is used for editing a clinic centre admin.
	 * @param clinicCentreAdmin This is a clinic centre admin object from the HTTP request.
	 * @param id This is the id of the edited clinic centre admin.
	 * @return ResponseEntity This returns the HTTP status code.
	 * */
	@PutMapping("/clinicCentreAdmins/{id}")
	public ResponseEntity<Object> editDoctor(@RequestBody ClinicCentreAdmin clinicCentreAdmin, @PathVariable("id") int id) {
		loadClinicCentreAdmins();
		int editedId = clinicCentreAdmin.getId();
		
		if (!clinicCentreAdmins.containsKey(id))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		clinicCentreAdmins.remove(id);
		clinicCentreAdmins.put(editedId, clinicCentreAdmin);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	/**
	 * This method is used for deleting a clinic centre admin.
	 * @param id This is the id of the deleted clinic centre admin.
	 * @return ResponseEntity This returns the HTTP status code.
	 * */
	@DeleteMapping("/clinicCentreAdmins/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") int id) {
		loadClinicCentreAdmins();
		clinicCentreAdmins.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	/**
	 * This method is used for loading doctors.
	 * @param Nothing.
	 * @return Nothing.
	 * */
	private void loadClinicCentreAdmins() {
		if (clinicCentreAdmins == null) {
			clinicCentreAdmins = new HashMap<Integer, ClinicCentreAdmin>();
			ClinicCentreAdmin a1 = new ClinicCentreAdmin("jedan@gmail.com", "sifra1", "Mario", "Kujundzic", "Fruskogorska", "NS", "RS", "1234", 1);
			ClinicCentreAdmin a2 = new ClinicCentreAdmin("dva@gmail.com", "sifra2", "Natasa", "Ivanovic", "Bulevar Oslobodjenja", "NS", "RS", "1235", 2);
			ClinicCentreAdmin a3 = new ClinicCentreAdmin("tri@gmail.com", "sifra3", "Bla", "Vajda", "Adice", "NS", "RS", "1236", 3);
			clinicCentreAdmins.put(a1.getId(), a1);
			clinicCentreAdmins.put(a2.getId(), a2);
			clinicCentreAdmins.put(a3.getId(), a3);
		}
			
		
	}
	
}
