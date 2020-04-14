package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.repository.PatientRepository;

public class PatientService {
	
	@Autowired
	private PatientRepository patients;
	
	public Patient findOne(long id) {
		return patients.findById(id).orElseGet(null);
	}

	public List<Patient> findAll() {
		return patients.findAll();
	}
	
	public Patient save(Patient doctor) {
		return patients.save(doctor);
	}

	public void remove(long id) {
		patients.deleteById(id);
	}


}
