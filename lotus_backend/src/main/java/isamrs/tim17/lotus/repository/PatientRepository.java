package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	Patient findOneById(Long id);
	Patient findOneByUsername(String username);
	Page<Patient> findAll(Pageable pageable);
	List<Patient> findAllBySurname(String surname);
	Page<Patient> findByNameContainsAndSurnameContainsAndSsidContainsAllIgnoringCase(String name, String surname, String ssid, Pageable pageable);
	List<Patient> findAllByEnabled(boolean enabled);
	
	

}
