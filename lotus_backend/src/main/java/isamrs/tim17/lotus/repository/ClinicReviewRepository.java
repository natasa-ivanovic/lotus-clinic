package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.ClinicReview;

public interface ClinicReviewRepository  extends JpaRepository<ClinicReview, Long> {
	
	ClinicReview findOneById(String id);
	List<ClinicReview> findAllByClinicId(Long id);
	Page<ClinicReview> findAll(Pageable pageable);

}
