package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctors;
	
	public Doctor findOne(long id) {
		return doctors.findById(id).orElseGet(null);
	}

	public List<Doctor> findAll() {
		return doctors.findAll();
	}
	
	public List<Doctor> findBySpecialty(AppointmentType specialty) {
		return doctors.findBySpecialty(specialty.toString());
	}
	
	public Doctor save(Doctor doctor) {
		return doctors.save(doctor);
	}

	public void remove(long id) {
		doctors.deleteById(id);
	}
	
	public Doctor findOne(String name, String surname) {
		return doctors.findOneByNameAndSurnameIgnoringCase(name, surname);
	}
	
	public boolean alreadyExistsUsername(String username) {
		return doctors.existsByUsername(username);
	}

}
