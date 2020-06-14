package isamrs.tim17.lotus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.RoomRequestType;
import isamrs.tim17.lotus.model.VacationRequest;

public interface RequestRepository extends JpaRepository<Request, Long> {
	@Query("SELECT a FROM RegistrationRequest a where a.status = 0")
	List<RegistrationRequest> getAllRegistrations();
	@Query("SELECT a FROM RegistrationRequest a where a.key = :key")
	List<RegistrationRequest> findByKey(@Param("key")String key);
	Request findOneById(Long id);
	@Query("SELECT a FROM RoomRequest a where a.status = 0")
	List<RoomRequest> getAllRoomRequests();
	Page<Request> findAll(Pageable pageable);

	@Query("SELECT a FROM RoomRequest a where a.date = :date AND :doctor member of a.doctors")
	RoomRequest findOneByDateAndDoctor(@Param("date") Date startDate, @Param("doctor") Doctor doctor);
	
	List<VacationRequest> findByClinicAndStatus(Clinic clinic, RequestStatus status);
	@Query("SELECT a FROM RoomRequest a where :doctor member of a.doctors AND (a.date BETWEEN :startDate AND :endDate) order by a.date")
	List<RoomRequest> findByDateRangeAndDoctor(@Param("doctor") Doctor d, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	//getuje samo requestove za appointmente
	@Query("SELECT a FROM RoomRequest a where (a.type = 0 OR a.type = 1) AND (a.date BETWEEN :startDate AND :endDate)")
	List<RoomRequest> findByDateRangeAndType(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
