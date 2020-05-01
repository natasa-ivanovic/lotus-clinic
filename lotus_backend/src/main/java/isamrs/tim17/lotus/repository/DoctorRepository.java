package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Doctor findOneById(String id);
	/*
	 * Pronalazi sve objekte tipa Doctor i vraca onoliko objekata koliko je
	 * specificirano kroz Pageable objekat. Npr. ako se prosledi objekat: new
	 * PageRequest(0, 10) vratice se nulta stranica sa prvih 10 objekata tipa
	 * Doctor.
	 */
	Page<Doctor> findAll(Pageable pageable);
	List<Doctor> findAllBySurname(String surname);
	List<Doctor> findByNameAndSurnameAllIgnoringCase(String name, String surname);


}
