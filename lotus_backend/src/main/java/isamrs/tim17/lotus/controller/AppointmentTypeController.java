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

import isamrs.tim17.lotus.dto.AppointmentTypeDTO;
import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.service.AppointmentTypeService;

@RestController
@RequestMapping("/api")
public class AppointmentTypeController {
	
	@Autowired
	private AppointmentTypeService service;
	
	@PostMapping("/appointmentTypes")
	public ResponseEntity<AppointmentType> addAppointmentType(@RequestBody String name) {
		System.out.println("Adding an appointment type...");
		System.out.println(name);
		
		AppointmentType at = new AppointmentType(name);
		service.save(at);
		return new ResponseEntity<>(at, HttpStatus.OK);
	}
	
	@GetMapping("/appointmentTypes")
	public ResponseEntity<List<AppointmentTypeDTO>> getAllAppointmentTypes() {
		List<AppointmentType> atypes = service.findAll();
		List<AppointmentTypeDTO> typesDTO = new ArrayList<>();
		
		for (AppointmentType at : atypes) {
			typesDTO.add(new AppointmentTypeDTO(at));
		}
		
		return new ResponseEntity<>(typesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/appointmentTypes/{id}")
	public ResponseEntity<AppointmentType> getAppointmentType(@PathVariable("id") long id) {
		AppointmentType at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(at, HttpStatus.OK);
	}
	
	@PutMapping("/appointmentTypes/{id}")
	public ResponseEntity<AppointmentType> updateAppointmentType(@RequestBody AppointmentType newAppointmentType, @PathVariable("id") long id) {
		
		if(id != newAppointmentType.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		AppointmentType at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		at.setName(newAppointmentType.getName());
		at = service.save(at);
		return new ResponseEntity<>(at, HttpStatus.OK);
	}
	
	@DeleteMapping("appointmentTypes/{id}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") long id) {
		System.out.println(id);
		AppointmentType at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		System.out.println("Deleting " + at);
		service.remove(id);
		return new ResponseEntity<>(at, HttpStatus.OK);
	}

}
