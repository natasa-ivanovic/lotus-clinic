package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctors;
	
	public Doctor findOne(int id) {
		return doctors.findById(id).orElseGet(null);
	}

	public List<Doctor> findAll() {
		return doctors.findAll();
	}
	
	public Doctor save(Doctor doctor) {
		return doctors.save(doctor);
	}

	public void remove(int id) {
		doctors.deleteById(id);
	}

}
