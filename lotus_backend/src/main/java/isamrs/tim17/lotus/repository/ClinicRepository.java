package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

	Clinic findOneById(String id);
	List<Clinic> findByNameAllIgnoringCase(String name);
	Page<Clinic> findAll(Pageable pagable);
}
