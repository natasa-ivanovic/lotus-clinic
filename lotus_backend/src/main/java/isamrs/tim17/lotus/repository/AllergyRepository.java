package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {

	Allergy findById(String id);
	Page<Allergy> findAll(Pageable pageable);
	List<Allergy> findByNameIgnoringCase(String name);
	
}
