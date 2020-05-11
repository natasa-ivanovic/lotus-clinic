package isamrs.tim17.lotus.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	@Query("SELECT a FROM Appointment a where a.doctor = :id AND (a.startDate BETWEEN :startDate AND :endDate) order by a.startDate")
	List<Appointment> getAllBetweenDates(@Param("id") Doctor id, @Param("startDate") Date startDate, @Param("endDate") Date endDate); //Date start, Date end);

}
