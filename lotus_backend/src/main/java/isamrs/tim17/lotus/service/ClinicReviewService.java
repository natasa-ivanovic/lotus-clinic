package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.repository.ClinicReviewRepository;

@Service
public class ClinicReviewService {
	
	@Autowired
	private ClinicReviewRepository reviewRepo;
	
	public ClinicReview findOne(long id) {
		return reviewRepo.findOneById(id);
	}

	public List<ClinicReview> findAll() {
		return reviewRepo.findAll();
	}

	public List<ClinicReview> findAllByClinic(Clinic c) {
		return reviewRepo.findAllByClinicId(c.getId());
	}
	
	public ClinicReview save(ClinicReview  at) {
		return reviewRepo.save(at);
	}

	public void remove(long id) {
		reviewRepo.deleteById(id);
	}
	
	

}