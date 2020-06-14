package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

import isamrs.tim17.lotus.dto.AllergyDTO;
import isamrs.tim17.lotus.dto.MedicalRecordDTO;
import isamrs.tim17.lotus.dto.PatientDTO;
import isamrs.tim17.lotus.dto.AppointmentDTO;
import isamrs.tim17.lotus.model.Allergy;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.service.AllergyService;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.MedicalRecordService;
import isamrs.tim17.lotus.service.PatientService;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService service;
	@Autowired
	private PatientService patientService;
	@Autowired
	private AppointmentService appService;
	@Autowired
	private AllergyService allergyService;

	@GetMapping("/medicalRecord/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<Object> getMedicalRecord(@PathVariable("id") long id) {
		Patient patient = patientService.findOne(id);
		PatientDTO patientDto = new PatientDTO(patient); // patient, record
		List<Appointment> finishedApps = appService.findFinished(patient.getMedicalRecord());
		List<AppointmentDTO> appDto = new ArrayList<>();
		// appointmentDto
		// operacije
		// bolesti
		for (Appointment app : finishedApps) {
			AppointmentDTO a = new AppointmentDTO(app);
			appDto.add(a);

		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("patient", patientDto);
		response.put("appointments", appDto);
		// response.put("operations", null);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@PutMapping("/medicalRecord")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord) {

		if (isEmptyOrNull(medicalRecord))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		MedicalRecord mr = service.findOne(medicalRecord.getId());
		if (mr == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		mr.setHeight(medicalRecord.getHeight());
		mr.setWeight(medicalRecord.getWeight());

		List<AllergyDTO> allergies = medicalRecord.getAllergies();
		HashSet<Allergy> allergySet = new HashSet<>();

		for (AllergyDTO a : allergies) {
			Allergy al = allergyService.findOne(a.getId());
			allergySet.add(al);
		}

		mr.setAllergies(allergySet);
		mr.setBloodType(medicalRecord.getBloodType());

		service.save(mr);
		return new ResponseEntity<>("Successfully updated medical record!", HttpStatus.OK);

	}

	private boolean isEmptyOrNull(MedicalRecordDTO mr) {
		if (mr == null)
			return true;
		return (mr.getBloodType() == null || "".equals(mr.getBloodType()));
	}

}
