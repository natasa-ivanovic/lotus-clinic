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
import isamrs.tim17.lotus.dto.PatientDTO;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.service.MedicalRecordService;
import isamrs.tim17.lotus.service.PatientService;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {
	
	@Autowired private MedicalRecordService service;
	@Autowired private PatientService patientService;

	@GetMapping("/medicalRecord/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<PatientDTO> getMedicalRecord(@PathVariable("id") long id) {
		
		Patient patient = patientService.findOne(id);
		PatientDTO dto = new PatientDTO(patient);
		return new ResponseEntity<>(dto, HttpStatus.OK); //record, patient
		
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
		return new ResponseEntity<>("Successfully updated medical record!", HttpStatus.OK);
				
	}
	
	private boolean isEmptyOrNull(MedicalRecordDTO mr) {
		if (mr == null)
			return true;
		if (mr.getAllergies() == null || "".equals(mr.getAllergies()))
			return true;
		if (mr.getBloodType() == null || "".equals(mr.getBloodType()))
			return true;
		return false;
	}
	



}
