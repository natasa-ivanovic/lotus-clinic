package isamrs.tim17.lotus.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.Diagnosis;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

	Diagnosis findById(String id);
	List<Diagnosis> findByNameAllIgnoringCase(String name);
	Page<Diagnosis> findAll(Pageable pagable);
	
	Page<Diagnosis> findAllByAppointmentsIn(Collection<Appointment> appointments, Pageable pagable);
}
