package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.ClinicalCentre;
import isamrs.tim17.lotus.repository.ClinicCentreRepository;

@Service
public class ClinicCentreService {

	@Autowired
	private ClinicCentreRepository centres;
	
	public ClinicalCentre findOne(long id) {
		return centres.findOneById(id);
	}
	
	public List<ClinicalCentre> findAll() {
		return centres.findAll();
	}
	
	public ClinicalCentre save(ClinicalCentre clinic) {
		return centres.save(clinic);
	}

	public void remove(long id) {
		centres.deleteById(id);
	}
	
	public ClinicalCentre findOneByName(String name) {
		return centres.findByNameAllIgnoringCase(name).get(0);
	}
}