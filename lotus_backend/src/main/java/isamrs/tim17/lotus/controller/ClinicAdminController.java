package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.ClinicAdminDTO;
import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.AuthorityService;
import isamrs.tim17.lotus.service.ClinicAdminService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.UserService;

@RestController
@RequestMapping("/api")
public class ClinicAdminController {
	
	@Autowired private ClinicAdminService service;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private AuthorityService authorityService;
	@Autowired private ClinicService clinicService;
	
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
	public ResponseEntity<ClinicAdministrator> getAdmin(@PathVariable("id") long id) {
		ClinicAdministrator admin = service.findOne(id);
		if (admin == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@PutMapping("/admins/self")
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
	
	@PostMapping("/admins")
	public ResponseEntity<Object> addClinicAdmin(@RequestBody ClinicAdminDTO admin) {
		System.out.println("Adding a clinic admin...");
		
		if(isEmptyOrNull(admin)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(service.alreadyExistsUsername(admin.getUsername())) {
			HashMap<String, String> error = new HashMap<>();
			error.put("text", "That username already exists!");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		
		Clinic clinic = clinicService.findOne(admin.getClinicId());

		
		ClinicAdministrator clinicAdmin = new ClinicAdministrator();
		
		clinicAdmin.setUsername(admin.getUsername());
		clinicAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
		clinicAdmin.setName(admin.getName());
		clinicAdmin.setSurname(admin.getSurname());
		clinicAdmin.setGender(admin.getGender());
		clinicAdmin.setBirthDate(admin.getBirthDate());
		clinicAdmin.setAddress(admin.getAddress());
		clinicAdmin.setCity(admin.getCity());
		clinicAdmin.setCountry(admin.getCountry());
		clinicAdmin.setPhoneNumber(admin.getPhoneNumber());
		clinicAdmin.setSsid(admin.getSsid());
		
		clinicAdmin.setClinic(clinic);
		clinicAdmin.setEnabled(true);
		ArrayList<Authority> auth = new ArrayList<Authority>();
		auth.add(authorityService.findByName("ROLE_ADMIN"));
		clinicAdmin.setAuthorities(auth);
		service.save(clinicAdmin);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private boolean isEmptyOrNull(ClinicAdminDTO clinicAdmin) {
		if (clinicAdmin == null)
			return true;
		if (clinicAdmin.getName() == null || "".equals(clinicAdmin.getName()))
			return true;
		if (clinicAdmin.getSurname() == null || "".equals(clinicAdmin.getSurname()))
			return true;
		if (clinicAdmin.getUsername() == null || "".equals(clinicAdmin.getUsername()))
			return true;
		if (clinicAdmin.getPassword() == null || "".equals(clinicAdmin.getPassword()))
			return true;
		if (clinicAdmin.getAddress() == null || "".equals(clinicAdmin.getAddress()))
			return true;
		if (clinicAdmin.getCity() == null || "".equals(clinicAdmin.getCity()))
			return true;
		if (clinicAdmin.getCountry() == null || "".equals(clinicAdmin.getCountry()))
			return true;
		if (clinicAdmin.getPhoneNumber() == null || "".equals(clinicAdmin.getPhoneNumber()))
			return true;
		if (clinicAdmin.getGender() == null || "".equals(clinicAdmin.getGender().toString()))
			return true;
		if (clinicAdmin.getBirthDate() == null || "".equals(clinicAdmin.getBirthDate().toString()))
			return true;
		return false;
	}

}
