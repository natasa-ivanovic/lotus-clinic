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
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Room;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	Appointment findOneById(Long id);
	Page<Appointment> findAll(Pageable pageable);
	List<Appointment> findByStatus(AppointmentStatus status);
	List<Appointment> findByMedicalRecord(MedicalRecord id);
	List<Appointment> findByDoctor(Doctor id);
	List<Appointment> findByClinicAndStatusAndReceptApproved(Clinic clinic, AppointmentStatus status, Boolean receptApproved);
	List<Appointment> findByDoctorAndStatusAndMedicalRecord(Doctor doctor, AppointmentStatus status, MedicalRecord medicalRecord);
	List<Appointment> findByDoctorAndMedicalRecord(Doctor doctor, MedicalRecord medicalRecord);

	
	Page<Appointment> findAllByMedicalRecordAndStatus(MedicalRecord id, AppointmentStatus status, Pageable pageable);

	@Query("SELECT a FROM Appointment a where a.doctor = :id AND (a.startDate BETWEEN :startDate AND :endDate) order by a.startDate")
	List<Appointment> getAllBetweenDates(@Param("id") Doctor id, @Param("startDate") Date startDate, @Param("endDate") Date endDate); //Date start, Date end);
	
	@Query("SELECT a FROM Appointment a where a.medicalRecord = :id AND a.status = 4 order by a.startDate")
	List<Appointment> getFinishedApps(@Param("id") MedicalRecord id);
	
	//0 - premade, 1 - scheduled, 3 - ongoing
	@Query("SELECT a FROM Appointment a JOIN Room r on a.room=r.id where a.room=:id AND (a.status = 0 OR a.status = 1 OR a.status = 3) AND (a.startDate BETWEEN :startDate AND :endDate) order by a.startDate")
	List<Appointment> getAppointmentsByRoomAndDate(@Param("id") Room id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	Appointment findByRegKey(String key);
}
