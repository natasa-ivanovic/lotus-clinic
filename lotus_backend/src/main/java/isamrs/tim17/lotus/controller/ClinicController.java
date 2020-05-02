package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
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

import isamrs.tim17.lotus.dto.ClinicDTO;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.service.ClinicService;

@RestController
@RequestMapping("/api")
public class ClinicController {

	@Autowired
	private ClinicService service;
	
	@GetMapping("/clinics")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {
		List<Clinic> clinics = service.findAll();
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for(Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}
		return new ResponseEntity<>(clinicsDTO, HttpStatus.OK);
	}
	
	@PostMapping("/clinics")
	public ResponseEntity<ClinicDTO> addClinic(@RequestBody Clinic clinic) {
		System.out.println("Adding a clinic...");
		System.out.println(clinic);
		
		service.save(clinic);
		System.out.println("Database is ok...");
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@GetMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> getClinic(@PathVariable("id") long id) {
		Clinic clinic = service.findOne(id);
		
		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@PutMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> updateMedicine(@RequestBody Clinic newClinic, @PathVariable long id) {
		if(id != newClinic.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Clinic clinic = service.findOne(id);
		
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		clinic.setName(newClinic.getName());
		clinic.setAddress(newClinic.getAddress());
		clinic.setDescription(newClinic.getDescription());
		clinic = service.save(clinic);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@DeleteMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> deleteRoom(@PathVariable("id") long id) {
		System.out.println(id);
		Clinic clinic = service.findOne(id);

		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		System.out.println("Deleting " + clinic);
		service.remove(id);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
}
