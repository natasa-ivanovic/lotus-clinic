package isamrs.tim17.lotus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.ClinicAdminService;

@RestController
@RequestMapping("/api")
public class ClinicAdminController {
	
	@Autowired private ClinicAdminService service;
	
	@GetMapping("/admins/self")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> getYourself() {
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			UserDTO admin = new UserDTO((User) a.getPrincipal());
			return new ResponseEntity<>(admin, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/admins/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<ClinicAdministrator> getAdmin(@PathVariable("id") long id) {
		ClinicAdministrator admin = service.findOne(id);
		if (admin == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@PutMapping("/admins/self")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateYourself(@RequestBody UserDTO admin) {
		ClinicAdministrator a = service.findOne(admin.getId());
		if (a == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		a.setName(admin.getName());
		a.setSurname(admin.getSurname());
		a.setBirthDate(admin.getBirthDate());
		a.setGender(admin.getGender());
		a.setAddress(admin.getAddress());
		a.setCity(admin.getCity());
		a.setCountry(admin.getCountry());
		a.setPhoneNumber(admin.getPhoneNumber());
		a = service.save(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
