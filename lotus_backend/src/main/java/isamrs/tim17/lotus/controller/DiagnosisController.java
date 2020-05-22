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

import isamrs.tim17.lotus.dto.DiagnosisDTO;
import isamrs.tim17.lotus.model.Diagnosis;
import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.service.DiagnosisService;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {
	
	@Autowired
	private DiagnosisService service;
	
	@GetMapping
	public ResponseEntity<List<DiagnosisDTO>> getAllDiagnoses() {
		List<Diagnosis> diagnoses = service.findAll();
		List<DiagnosisDTO> diagnosesDTO = new ArrayList<>();
		for(Diagnosis d : diagnoses) {
			diagnosesDTO.add(new DiagnosisDTO(d));
		}
		return new ResponseEntity<List<DiagnosisDTO>>(diagnosesDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody String name) {
		System.out.println("Adding a diagnosis...");
		System.out.print(name);
		
		Diagnosis diagnosis = new Diagnosis(name);
		service.save(diagnosis);
		System.out.println("Database is ok...");
		return new ResponseEntity<Diagnosis>(diagnosis, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DiagnosisDTO> getDiagnosis(@PathVariable("id") long id) {
		Diagnosis diagnosis = service.findOne(id);
		
		if(diagnosis == null)
			return new ResponseEntity<DiagnosisDTO>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<DiagnosisDTO>(new DiagnosisDTO(diagnosis), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DiagnosisDTO> updateDiagnosis(@RequestBody Diagnosis newDiagnosis, @PathVariable long id) {
		
		if (id != newDiagnosis.getId())
			return new ResponseEntity<DiagnosisDTO>(HttpStatus.BAD_REQUEST);
		
		Diagnosis diagnosis = service.findOne(newDiagnosis.getId());
		
		if (diagnosis == null)
			return new ResponseEntity<DiagnosisDTO>(HttpStatus.BAD_REQUEST);
		
		diagnosis.setName(newDiagnosis.getName());
		diagnosis = service.save(diagnosis);
		
		return new ResponseEntity<DiagnosisDTO>(new DiagnosisDTO(diagnosis), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DiagnosisDTO> deleteDiagnosis(@PathVariable("id") long id) {
		System.out.println(id);
		Diagnosis diagnosis = service.findOne(id);

		if (diagnosis == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		System.out.println("Deleting " + diagnosis);
		service.remove(id);
		return new ResponseEntity<>(new DiagnosisDTO(diagnosis), HttpStatus.OK);
	}
}
