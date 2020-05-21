package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Allergy;
import isamrs.tim17.lotus.repository.AllergyRepository;

@Service
public class AllergyService {
	
	@Autowired
	private AllergyRepository allergies;
	
	public Allergy findOne(long id) {
		return allergies.findById(id).orElse(null);
	}
	
	public List<Allergy> findAll() {
		return allergies.findAll();
	}
	
	public Allergy save(Allergy allergy) {
		return allergies.save(allergy);
	}
	
	public void remove(long id) {
		allergies.deleteById(id);
	}

}
