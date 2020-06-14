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

import isamrs.tim17.lotus.dto.AppointmentPriceDTO;
import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.AppointmentPriceService;
import isamrs.tim17.lotus.service.AppointmentTypeService;

@RestController
@RequestMapping("/api/appointmentPrices")
public class AppointmentPriceController {
	
	@Autowired
	private AppointmentPriceService service;
	@Autowired
	private AppointmentTypeService typeService;
	
	@GetMapping("")
	@PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
	public ResponseEntity<List<AppointmentPriceDTO>> getAllAppointmentPrices() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		List<AppointmentPrice> priceList = new ArrayList<>();
		
		if(user.getRole().equals("ADMIN"))
			priceList = service.findByClinicId(((ClinicAdministrator) user).getClinic());
		else if (user.getRole().equals("DOCTOR"))
			priceList = service.findByClinicId(((Doctor) user).getClinic());
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<AppointmentPriceDTO> priceListDTO = new ArrayList<>();
		for(AppointmentPrice ap : priceList)
		{
			priceListDTO.add(new AppointmentPriceDTO(ap));
		}
		return new ResponseEntity<>(priceListDTO, HttpStatus.OK);
	}
	
	@GetMapping("/apps")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AppointmentPriceDTO>> getAllAppointmentPricesNoOperation() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		List<AppointmentPrice> priceList = new ArrayList<>();
		
		if(user.getRole().equals("ADMIN"))
			priceList = service.findByClinicId(((ClinicAdministrator) user).getClinic());
		else if (user.getRole().equals("DOCTOR"))
			priceList = service.findByClinicId(((Doctor) user).getClinic());
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<AppointmentPriceDTO> priceListDTO = new ArrayList<>();
		for(AppointmentPrice ap : priceList)
		{
			if (!ap.getType().isOperation())
				priceListDTO.add(new AppointmentPriceDTO(ap));
		}
		return new ResponseEntity<>(priceListDTO, HttpStatus.OK);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addAppointmentPrice(@RequestBody AppointmentPriceDTO appDTO)
	{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();
		
		AppointmentPrice appPrice = new AppointmentPrice();
		appPrice.setPrice(appDTO.getPrice());
		appPrice.setDiscount(appDTO.getDiscount());
		appPrice.setType(typeService.findOne(appDTO.getType_id()));
		appPrice.setClinic(admin.getClinic());
		
		service.save(appPrice);
		
		return null;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppointmentPriceDTO> getAppointmentPrice(@PathVariable("id") long id) {
		AppointmentPrice ap = service.findOne(id);
		AppointmentPriceDTO apDTO = new AppointmentPriceDTO(ap);
		if(ap == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateAppointmentPrice(@RequestBody AppointmentPriceDTO newAppointmentPrice, @PathVariable("id") long id) {
		if(id != newAppointmentPrice.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		AppointmentPrice at = service.findOne(id);
		
		if (at == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		at.setPrice(newAppointmentPrice.getPrice());
		at.setDiscount(newAppointmentPrice.getDiscount());
		service.save(at);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteType(@PathVariable("id") long id) {
		AppointmentPrice at = service.findOne(id);

		if (at == null)
			return new ResponseEntity<>("Appointment type not found!", HttpStatus.BAD_REQUEST);
		try { 
			service.remove(at);			
		} catch (Exception e) {
			return new ResponseEntity<>("Appointment price is in use!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(at, HttpStatus.OK);
	}
}
