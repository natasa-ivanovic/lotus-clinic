package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.tim17.lotus.model.Nurse;
import isamrs.tim17.lotus.repository.NurseRepository;

public class NurseService {

	@Autowired
	private NurseRepository nurses;
	
	public Nurse findOne(long id) {
		return nurses.findById(id).orElseGet(null);
	}

	public List<Nurse> findAll() {
		return nurses.findAll();
	}
	
	public Nurse save(Nurse doctor) {
		return nurses.save(doctor);
	}

	public void remove(long id) {
		nurses.deleteById(id);
	}

}
