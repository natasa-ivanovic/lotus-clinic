package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Doctor findOneById(Long id);
	
	Page<Doctor> findAll(Pageable pageable);
	
	List<Doctor> findAllBySurname(String surname);
	List<Doctor> findByNameAndSurnameAllIgnoringCase(String name, String surname);
	List<Doctor> findBySpecialty(String specialty);
	List<Doctor> findAllByClinic(Clinic clinic);
	Doctor findOneByNameAndSurnameIgnoringCase(String name, String surname);
	boolean existsById(long id);
	boolean existsByUsername(String username);


}
