package isamrs.tim17.lotus.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MedicalRecord;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	Appointment findOneById(String id);
	Page<Appointment> findAll(Pageable pageable);
	List<Appointment> findByStatus(AppointmentStatus status);
	List<Appointment> findByMedicalRecord(MedicalRecord id);
	List<Appointment> findByDoctor(Doctor id);
}