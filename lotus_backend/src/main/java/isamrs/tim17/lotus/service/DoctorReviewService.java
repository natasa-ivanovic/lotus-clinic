package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.DoctorReview;
import isamrs.tim17.lotus.repository.DoctorReviewRepository;

@Service
public class DoctorReviewService {
	
	@Autowired
	private DoctorReviewRepository reviewRepo;
	
	public DoctorReview findOne(long id) {
		return reviewRepo.findOneById(id);
	}

	public List<DoctorReview> findAll() {
		return reviewRepo.findAll();
	}

	public List<DoctorReview> findAllByDoctor(Doctor d) {
		return reviewRepo.findAllByDoctorId(d.getId());
	}
	
	public DoctorReview save(DoctorReview  at) {
		return reviewRepo.save(at);
	}

	public void remove(long id) {
		reviewRepo.deleteById(id);
	}
	
	

}