package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.MedicineDTO;
import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.service.MedicineService;

@RestController
@RequestMapping("/api")
public class MedicineController {
	
	@Autowired
	private MedicineService service;
	
	@GetMapping("/medicines")
	public ResponseEntity<List<MedicineDTO>> getAllMedicines() {
		List<Medicine> medicines = service.findAll();
		List<MedicineDTO> medicinesDTO = new ArrayList<>();
		for(Medicine m : medicines) {
			medicinesDTO.add(new MedicineDTO(m));
		}
		return new ResponseEntity<>(medicinesDTO, HttpStatus.OK);
	}
	
	@PostMapping("/medicines")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<Object> addClinic(@RequestBody String name) {
		if (name == null || "".equals(name))
			return new ResponseEntity<>("Name cannot be empty!", HttpStatus.BAD_REQUEST);
		
		Medicine medicine = new Medicine(name);
		try {
			service.save(medicine);
		}catch (Exception e) {
			return new ResponseEntity<>("Someone saved at the same time!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(medicine, HttpStatus.OK);
	}
	
	@DeleteMapping("/medicines/{id}")
	@PreAuthorize("hasRole('CENTRE_ADMIN')")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") long id) {
		Medicine medicine = service.findOne(id);

		if (medicine == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			service.remove(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Someone deleted at the same time!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(medicine, HttpStatus.OK);
	}
	
	@GetMapping("/medicines/{id}")
	public ResponseEntity<Medicine> getMedicine(@PathVariable("id") long id) {
		
		Medicine medicine = service.findOne(id);

		// room must exist
		if (medicine == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(medicine, HttpStatus.OK);

	}
	
	@PutMapping("/medicines/{id}")
	public ResponseEntity<Medicine> updateMedicine(@RequestBody MedicineDTO newMedicine, @PathVariable long id) {
		
		if(newMedicine == null || "".equals(newMedicine.getName()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if (id != newMedicine.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// a room must exist
		Medicine medicine = service.findOne(newMedicine.getId());

		if (medicine == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		medicine.setName(newMedicine.getName());
		medicine = service.save(medicine);
		return new ResponseEntity<>(medicine, HttpStatus.OK);
	}
}
