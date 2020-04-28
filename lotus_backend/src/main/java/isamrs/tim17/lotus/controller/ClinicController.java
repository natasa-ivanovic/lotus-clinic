package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.ClinicDTO;
import isamrs.tim17.lotus.model.Clinic;
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
	public ResponseEntity<Clinic> addClinic(@RequestBody Clinic clinic)
	{
		System.out.println("Adding a clinic...");
		System.out.println(clinic);
		
		service.save(clinic);
		System.out.println("Database is ok...");
		return new ResponseEntity<>(clinic, HttpStatus.OK);
	}
}
