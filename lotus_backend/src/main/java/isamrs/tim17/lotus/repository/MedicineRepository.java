package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	Medicine findOneById(String id);
	List<Medicine> findByNameAllIgnoringCase(String name);
	Page<Medicine> findAll(Pageable pagable);
}
