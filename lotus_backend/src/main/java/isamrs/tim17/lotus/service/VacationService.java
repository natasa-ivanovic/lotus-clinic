package isamrs.tim17.lotus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Vacation;
import isamrs.tim17.lotus.repository.VacationRepository;

@Service
public class VacationService {
	
	@Autowired
	private VacationRepository vacations;
	
	public Vacation save(Vacation vacation) {
		return vacations.save(vacation);
	}
}
