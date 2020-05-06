package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Diagnosis;
import isamrs.tim17.lotus.repository.DiagnosisRepository;

@Service
public class DiagnosisService {

	@Autowired
	private DiagnosisRepository diagnoses;
	
	public Diagnosis findOne(long id) {
		return diagnoses.findById(id).orElse(null);
	}
	
	public List<Diagnosis> findAll(){
		return diagnoses.findAll();
	}
	
	public Diagnosis save(Diagnosis diagnosis) {
		return diagnoses.save(diagnosis);
	}
	
	public void remove(long id ) {
		diagnoses.deleteById(id);
	}
}
