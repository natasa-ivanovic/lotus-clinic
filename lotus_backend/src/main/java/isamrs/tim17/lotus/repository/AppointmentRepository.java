package isamrs.tim17.lotus.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	Appointment findOneById(String id);
	Page<Appointment> findAll(Pageable pageable);
}
