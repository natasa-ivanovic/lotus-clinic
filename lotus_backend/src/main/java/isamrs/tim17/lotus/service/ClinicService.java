package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.repository.ClinicRepository;

@Service
@Transactional(readOnly = true)
public class ClinicService {

	@Autowired
	private ClinicRepository clinics;
	
	public Clinic findOne(long id) {
		return clinics.findOneById(id);
	}
	
	public List<Clinic> findAll() {
		return clinics.findAll();
	}

	@Transactional(readOnly = false)
	public Clinic save(Clinic clinic) {
		return clinics.save(clinic);
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		clinics.deleteById(id);
	}
	
	public Clinic findOneByName(String name) {
		return clinics.findByNameAllIgnoringCase(name).get(0);
	}
}
