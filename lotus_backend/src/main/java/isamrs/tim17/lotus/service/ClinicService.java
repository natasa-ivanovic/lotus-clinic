package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.repository.ClinicRepository;

@Service
public class ClinicService {

	@Autowired
	private ClinicRepository clinics;
	
	public Clinic findOne(long id) {
		return clinics.findById(id).orElseGet(null);
	}
	
	public List<Clinic> findAll() {
		return clinics.findAll();
	}
	
	public Clinic save(Clinic clinic) {
		return clinics.save(clinic);
	}

	public void remove(long id) {
		clinics.deleteById(id);
	}
	
	public Clinic findOneByName(String name) {
		return clinics.findByNameAllIgnoringCase(name).get(0);
	}
}
