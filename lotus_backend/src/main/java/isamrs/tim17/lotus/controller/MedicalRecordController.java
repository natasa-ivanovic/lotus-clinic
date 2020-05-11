package isamrs.tim17.lotus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.MedicalRecordDTO;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.service.MedicalRecordService;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {
	
	@Autowired private MedicalRecordService service;

	@GetMapping("/medicalRecord/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable("id") long id) {
		
		MedicalRecord mr = service.findOne(id);
		if (mr == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		MedicalRecordDTO mrDTO = new MedicalRecordDTO(mr);
		
		return new ResponseEntity<>(mrDTO, HttpStatus.OK);
		
	}
	
	@PutMapping("/medicalRecord")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord) {
		
		if (isEmptyOrNull(medicalRecord) == true)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		MedicalRecord mr = service.findOne(medicalRecord.getId());
		if (mr == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		mr.setHeight(medicalRecord.getHeight());
		mr.setWeight(medicalRecord.getWeight());
		mr.setAllergies(medicalRecord.getAllergies());
		mr.setBloodType(medicalRecord.getBloodType());
		
		mr = service.save(mr);
		return new ResponseEntity<>(HttpStatus.OK);
				
	}
	
	private boolean isEmptyOrNull(MedicalRecordDTO mr) {
		if (mr == null)
			return false;
		if (mr.getAllergies() == null || "".equals(mr.getAllergies()))
			return false;
		if (mr.getBloodType() == null || "".equals(mr.getBloodType()))
			return false;
		return true;
	}
	



}
