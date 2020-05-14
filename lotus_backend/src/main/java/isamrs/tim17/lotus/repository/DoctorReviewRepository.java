package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.DoctorReview;
public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long> {
	
	DoctorReview findOneById(String id);
	List<DoctorReview> findAllByDoctorId(Long id);
	Page<DoctorReview> findAll(Pageable pageable);

}
