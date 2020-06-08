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

import isamrs.tim17.lotus.dto.AllergyDTO;
import isamrs.tim17.lotus.model.Allergy;
import isamrs.tim17.lotus.service.AllergyService;

@RestController
@RequestMapping("/api/allergies")
public class AllergyController {
	
	@Autowired
	private AllergyService service;
	
	@GetMapping
	public ResponseEntity<List<AllergyDTO>> getAllAllergies() {
		List<Allergy> allergies = service.findAll();
		List<AllergyDTO> allergiesDTO = new ArrayList<>();
		for(Allergy d : allergies) {
			allergiesDTO.add(new AllergyDTO(d));
		}
		return new ResponseEntity<>(allergiesDTO, HttpStatus.OK);

	}
	
	@PostMapping
	public ResponseEntity<Object> addAllergy(@RequestBody String name) {
		Allergy allergy = new Allergy(name);
		service.save(allergy);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AllergyDTO> getAllergy(@PathVariable("id") long id) {
		Allergy allergy = service.findOne(id);
		
		if(allergy == null)	
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(new AllergyDTO(allergy), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AllergyDTO> updateAllergy(@RequestBody AllergyDTO newAllergy, @PathVariable long id) {
		
		if (id != newAllergy.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Allergy allergy = service.findOne(newAllergy.getId());
		
		if (allergy == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		allergy.setName(newAllergy.getName());
		allergy = service.save(allergy);
		
		return new ResponseEntity<>(new AllergyDTO(allergy), HttpStatus.OK);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AllergyDTO> deleteAllergy(@PathVariable("id") long id) {
		Allergy allergy = service.findOne(id);

		if (allergy == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		service.remove(id);
		return new ResponseEntity<>(new AllergyDTO(allergy), HttpStatus.OK);
	}

}
