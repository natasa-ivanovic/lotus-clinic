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

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.service.DoctorService;

@RestController
@RequestMapping("/api")
public class DoctorController {

	@Autowired
	private DoctorService service;

	/**
	 * This method is used for adding a doctor.
	 * 
	 * @param doctor This is a doctor object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PostMapping("/doctors")
	public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
		System.out.println("Adding a doctor...");
		/*
		 * if (isEmptyOrNull(doctor)) { System.out.println("Something's wrong...");
		 * return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
		 */
		System.out.println("Doctor is ok...");
		service.save(doctor);
		System.out.println("Database is ok...");
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a list of doctors.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of doctors and the HTTP status
	 *         code.
	 */
	@GetMapping("/doctors")
	public ResponseEntity<List<UserDTO>> getAllDoctors() {
		List<Doctor> doctors = service.findAll();

		// convert doctors to DTOs
		List<UserDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new UserDTO(d));
		}

		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a doctor.
	 * 
	 * @param id This is requested doctor's id.
	 * @return Doctor This returns the requested doctor.
	 */
	@GetMapping("/doctors/{id}")
	public ResponseEntity<Doctor> getDoctor(@PathVariable("id") int id) {
		Doctor doctor = service.findOne(id);

		// doctor must exist
		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(doctor, HttpStatus.OK);

	}

	/**
	 * This method is used for editing a doctor.
	 * 
	 * @param doctor This is a doctor object from the HTTP request.
	 * @param id     This is the id of the edited doctor.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/doctors/{id}")
	public ResponseEntity<UserDTO> updateDoctor(@RequestBody UserDTO newDoctor) {

		// a doctor must exist
		Doctor doctor = service.findOne(newDoctor.getId());

		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		doctor.setName(newDoctor.getName());
		doctor.setSurname(newDoctor.getSurname());
		doctor.setEmail(newDoctor.getEmail());
		doctor.setPassword(newDoctor.getPassword());
		doctor.setBirthDate(newDoctor.getBirthDate());
		doctor.setGender(newDoctor.getGender());
		doctor.setAddress(newDoctor.getAddress());
		doctor.setCity(newDoctor.getCity());
		doctor.setCountry(newDoctor.getCountry());
		doctor.setPhoneNumber(newDoctor.getPhoneNumber());

		doctor = service.save(doctor);
		return new ResponseEntity<>(new UserDTO(doctor), HttpStatus.OK);

	}

	/**
	 * This method is used for deleting a doctor.
	 * 
	 * @param id This is the id of the deleted doctor.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<Object> deleteDoctor(@PathVariable("id") int id) {

		Doctor doctor = service.findOne(id);

		if (doctor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		service.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	private boolean isEmptyOrNull(Doctor doctor) {
		if (doctor.getName() == null || "".equals(doctor.getName()))
			return true;
		if (doctor.getSurname() == null || "".equals(doctor.getSurname()))
			return true;
		if (doctor.getEmail() == null || "".equals(doctor.getEmail()))
			return true;
		if (doctor.getPassword() == null || "".equals(doctor.getPassword()))
			return true;
		if (doctor.getAddress() == null || "".equals(doctor.getAddress()))
			return true;
		if (doctor.getCity() == null || "".equals(doctor.getCity()))
			return true;
		if (doctor.getCountry() == null || "".equals(doctor.getCountry()))
			return true;
		if (doctor.getPhoneNumber() == null || "".equals(doctor.getPhoneNumber()))
			return true;
		if (doctor.getGender() == null || "".equals(doctor.getGender().toString()))
			return true;
		if (doctor.getBirthDate() == null || "".equals(doctor.getBirthDate().toString()))
			return true;
		return false;

	}
	 

}
