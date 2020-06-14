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
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.ClinicalCentre;
import isamrs.tim17.lotus.model.ClinicalCentreAdministrator;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.ClinicService;

@RestController
@RequestMapping("/api")
public class ClinicController {

	@Autowired
	private ClinicService service;
	
	@GetMapping("/clinics")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
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
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<ClinicDTO> getClinic(@PathVariable("id") long id) {
		Clinic clinic = service.findOne(id);
		
		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@GetMapping("/clinics/myClinic")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getMyClinic() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		
		if (admin == null)
			return new ResponseEntity<>("Only allowed to clinic administrators!", HttpStatus.BAD_REQUEST);
		
		Clinic clinic = service.findOne(admin.getClinic().getId());
		ClinicDTO clinicDTO = new ClinicDTO(clinic);
		
		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
		
	}
	
	@PutMapping("/clinics/{id}")
	@PreAuthorize("hasAnyRole('CENTRE_ADMIN', 'ADMIN')")
	public ResponseEntity<Object> updateClinic(@RequestBody ClinicDTO newClinic, @PathVariable long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		Clinic clinic = null;
		if (user.getRole().equals("ADMIN")) {
			if (((ClinicAdministrator) user).getClinic().getId() != newClinic.getId()) {
				return new ResponseEntity<>("Not allowed to update this clinic!", HttpStatus.BAD_REQUEST);
			}
			clinic = service.findOne(((ClinicAdministrator) user).getClinic().getId());
		}
		// ako admin klinike apdejtuje, id je 0 - zato ovo u else
		else if (user.getRole().equals("CENTRE_ADMIN")) {
			if(id != newClinic.getId())
				return new ResponseEntity<>("Incompatible clinic and id!", HttpStatus.BAD_REQUEST);
			clinic = service.findOne(id);
		}
		
		if(clinic == null)
			return new ResponseEntity<>("Clinic is not found!", HttpStatus.BAD_REQUEST);
		
		clinic.setName(newClinic.getName());
		clinic.setAddress(newClinic.getAddress());
		clinic.setDescription(newClinic.getDescription());
		try {
			service.save(clinic);		
		} catch (Exception e) {
			return new ResponseEntity<>("Clinic was updated by somebody else!", HttpStatus.BAD_REQUEST);
		}
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
