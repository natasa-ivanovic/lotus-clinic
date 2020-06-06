package isamrs.tim17.lotus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Nurse;
import isamrs.tim17.lotus.service.NurseService;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {
	
	@Autowired
	NurseService service;
	
	@GetMapping("/self")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<UserDTO> getYourself() {
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			UserDTO nurse = new UserDTO((Nurse) a.getPrincipal());
			return new ResponseEntity<>(nurse, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/self")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<Object> updateYourself(@RequestBody UserDTO nurse) {
		Nurse nurseToUpdate = service.findOne(nurse.getId());
		if (nurseToUpdate == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		nurseToUpdate.setName(nurse.getName());
		nurseToUpdate.setSurname(nurse.getSurname());
		nurseToUpdate.setBirthDate(nurse.getBirthDate());
		nurseToUpdate.setGender(nurse.getGender());
		nurseToUpdate.setAddress(nurse.getAddress());
		nurseToUpdate.setCity(nurse.getCity());
		nurseToUpdate.setCountry(nurse.getCountry());
		nurseToUpdate.setPhoneNumber(nurse.getPhoneNumber());
		service.save(nurseToUpdate);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
