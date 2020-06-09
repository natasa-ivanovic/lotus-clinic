package isamrs.tim17.lotus.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Appointment;
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
	
	public Page<Diagnosis> findAllByAppointments(Collection<Appointment> appointments, Pageable pagable) {
		return diagnoses.findAllByAppointmentsIn(appointments, pagable);
	}
	
	public Diagnosis save(Diagnosis diagnosis) {
		return diagnoses.save(diagnosis);
	}
	
	public void remove(long id ) {
		diagnoses.deleteById(id);
	}
}
