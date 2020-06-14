package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.AppointmentType;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
	
	AppointmentType findOneById(Long id);
	List<AppointmentType> findByNameAllIgnoringCase(String name);
	Page<AppointmentType> findAll(Pageable pageable);
	AppointmentType findOneByNameIgnoringCase(String name);
	List<AppointmentType> findAllByOperation(boolean b);

}