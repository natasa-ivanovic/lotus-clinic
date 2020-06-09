package isamrs.tim17.lotus.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.OperationDTO;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.OperationService;
import isamrs.tim17.lotus.service.PatientService;

@RestController
@RequestMapping("/api/operations")
public class OperationController {
	@Autowired
	private OperationService service;
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/patient/past")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> getMyPastOperations(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();

		return getPastOperations(patient.getMedicalRecord(), pageNo, pageSize, sortBy, descending);		
	}

	@GetMapping("/patient/{id}/past")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<Object> getPatientsPastAppointments(@PathVariable("id") String id, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		long patientId;
		try {
			patientId = Long.parseLong(id);			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Forwarded id isn't a number", HttpStatus.BAD_REQUEST);
		}
		if (user.getRole().equals("PATIENT") && user.getId() != patientId)
			return new ResponseEntity<>("Cannot get another patient's medical history!", HttpStatus.BAD_REQUEST);
		Patient patient = patientService.findOne(patientId);
		if (patient == null)
			return new ResponseEntity<>("Patient with specified ID doesn't exist in database!", HttpStatus.BAD_REQUEST);
		return getPastOperations(patient.getMedicalRecord(), pageNo, pageSize, sortBy, descending);
	}
	
	private ResponseEntity<Object> getPastOperations(MedicalRecord medicalRecord, Integer pageNo, Integer pageSize,
			String sortBy, String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<Operation> operations = null;
		try {
			operations = service.findByMedicalRecord(medicalRecord, OperationStatus.FINISHED, paging);
		} catch (Exception e) {
			return new ResponseEntity<>("Error in forwarded arguments for sort!", HttpStatus.BAD_REQUEST);
		}
		if (operations == null)
			return new ResponseEntity<>("Something went wrong. Please try again later.", HttpStatus.BAD_REQUEST);
		Page<OperationDTO> dto = operations.map(new Function<Operation, OperationDTO>() {
			@Override
			public OperationDTO apply(Operation o) {
				return new OperationDTO(o);
			}
		});
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
}
