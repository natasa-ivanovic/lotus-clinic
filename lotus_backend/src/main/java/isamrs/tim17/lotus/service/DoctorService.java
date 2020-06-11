package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.repository.DoctorRepository;

@Service
@Transactional(readOnly = true)
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctors;
	
	public Doctor findOne(long id) {
		return doctors.findOneById(id);
	}

	public List<Doctor> findAll() {
		return doctors.findAll();
	}
	
	public List<Doctor> findBySpecialty(AppointmentType specialty) {
		return doctors.findBySpecialty(specialty.toString());
	}

	public List<Doctor> findByClinic(Clinic clinic) {
		return doctors.findAllByClinic(clinic);
	}
	
	@Transactional(readOnly = false)
	public Doctor save(Doctor doctor) {
		return doctors.save(doctor);
	}

	@Transactional(readOnly = false)
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
