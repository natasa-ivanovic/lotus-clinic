package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {
	
	Nurse findOneById(String id);
	Page<Nurse> findAll(Pageable pageable);
	List<Nurse> findAllBySurname(String surname);
	List<Nurse> findByNameAndSurnameAllIgnoringCase(String name, String surname);


}
