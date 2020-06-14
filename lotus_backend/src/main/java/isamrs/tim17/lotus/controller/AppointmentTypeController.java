package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<AppointmentType> addAppointmentType(@RequestBody AppointmentTypeDTO appointmentDTO) {
		if (appointmentDTO == null || "".equals(appointmentDTO.getName()) || appointmentDTO.getName() == null || appointmentDTO.getPrice() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		AppointmentType at = new AppointmentType(appointmentDTO.getName(), appointmentDTO.isOperation());
		service.save(at);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/appointmentTypes/pages")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<Object> getAllAppointmentsPages(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<AppointmentType> apps = null;
		
		try {
			apps = service.findAllPage(paging);

		} catch (Exception e) {
			return new ResponseEntity<>("Error in forwarded arguments for sort!", HttpStatus.BAD_REQUEST);
		}
		
		if (apps == null)
			return new ResponseEntity<>("Something went wrong. Please try again later.", HttpStatus.BAD_REQUEST);
		Page<AppointmentTypeDTO> dto = apps.map(new Function<AppointmentType, AppointmentTypeDTO>() {
			@Override
			public AppointmentTypeDTO apply(AppointmentType app) {
				return new AppointmentTypeDTO(app);
			}
		});
		return new ResponseEntity<>(dto, HttpStatus.OK);


		
		
		
	}
	
	@GetMapping("/appointmentTypes")
	@PreAuthorize("hasAnyRole('PATIENT, CENTRE_ADMIN, ADMIN')")
	public ResponseEntity<List<AppointmentTypeDTO>> getAllAppointmentTypes() {
		List<AppointmentType> atypes = service.findAll();
		List<AppointmentTypeDTO> typesDTO = new ArrayList<>();
		
		for (AppointmentType at : atypes) {
			typesDTO.add(new AppointmentTypeDTO(at));
		}
		
		return new ResponseEntity<>(typesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/appointmentTypes/appointments")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<AppointmentTypeDTO>> getAllAppointmentTypesNoOperations() {
		List<AppointmentType> atypes = service.findAllAppointments();
		List<AppointmentTypeDTO> typesDTO = new ArrayList<>();
		
		for (AppointmentType at : atypes) {
			typesDTO.add(new AppointmentTypeDTO(at));
		}
		
		return new ResponseEntity<>(typesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/appointmentTypes/{id}")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<AppointmentTypeDTO> getAppointmentType(@PathVariable("id") long id) {
		AppointmentType at = service.findOne(id);
		AppointmentTypeDTO dto = new AppointmentTypeDTO(at);
		if (at == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping("/appointmentTypes/{id}")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<Object> updateAppointmentType(@RequestBody AppointmentTypeDTO newAppointmentType, @PathVariable("id") long id) {
		
		if(id != newAppointmentType.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		AppointmentType at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		at.setName(newAppointmentType.getName());
		at.setOperation(newAppointmentType.isOperation());
		service.save(at);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("appointmentTypes/{id}")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") long id) {
		AppointmentType at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		service.remove(id);
		return new ResponseEntity<>(at, HttpStatus.OK);
	}

}
