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

import isamrs.tim17.lotus.dto.ClinicDTO;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicalCentre;
import isamrs.tim17.lotus.model.ClinicalCentreAdministrator;
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
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<ClinicDTO> addClinic(@RequestBody ClinicDTO clinic) {
		ClinicalCentre cCentre;
		Clinic c = new Clinic(clinic);
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			ClinicalCentreAdministrator admin = (ClinicalCentreAdministrator) a.getPrincipal();
			cCentre = admin.getClinicalCentre();
			if (cCentre == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			c.setClinicalCentre(cCentre);				
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);			
		}
		service.save(c);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> getClinic(@PathVariable("id") long id) {
		Clinic clinic = service.findOne(id);
		
		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@PutMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO newClinic, @PathVariable long id) {
		if(id != newClinic.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Clinic clinic = service.findOne(id);
		
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		clinic.setName(newClinic.getName());
		clinic.setAddress(newClinic.getAddress());
		clinic.setDescription(newClinic.getDescription());
		service.save(clinic);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/clinics/{id}")
	public ResponseEntity<ClinicDTO> deleteRoom(@PathVariable("id") long id) {
		Clinic clinic = service.findOne(id);

		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		service.remove(id);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
}
