package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		return new ResponseEntity<List<MedicineDTO>>(medicinesDTO, HttpStatus.OK);
	}
	
	@PostMapping("/medicines")
	public ResponseEntity<Medicine> addClinic(@RequestBody Medicine medicine) {
		System.out.println("Adding a medicine...");
		System.out.println(medicine);
		
		service.save(medicine);
		System.out.println("Database is ok...");
		return new ResponseEntity<Medicine>(medicine, HttpStatus.OK);
	}
}
