package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.ClinicalCentre;

public interface ClinicCentreRepository   extends JpaRepository<ClinicalCentre, Long>{

	ClinicalCentre findOneById(Long id);
	List<ClinicalCentre> findByNameAllIgnoringCase(String name);
	Page<ClinicalCentre> findAll(Pageable pagable);
}
