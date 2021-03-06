package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patients;
	
	public Patient findOne(long id) {
		return patients.findOneById(id);
	}
	
	public Patient findOneByUsername(String email) {
		return patients.findOneByUsername(email);
	}

	public List<Patient> findAll() {
		return patients.findAll();
	}
	
	public List<Patient> findByStatus(boolean status) {
		return patients.findAllByEnabled(status);
	}
	
	public Patient save(Patient doctor) {
		return patients.save(doctor);
	}

	public void remove(long id) {
		patients.deleteById(id);
	}
	
	public Page<Patient> findPatients(String name, String surname, String ssid, Pageable pageable) {
		return patients.findByNameContainsAndSurnameContainsAndSsidContainsAllIgnoringCase(name, surname, ssid, pageable);
	}

}
