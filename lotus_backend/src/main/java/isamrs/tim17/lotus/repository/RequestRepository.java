package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RoomRequest;

public interface RequestRepository extends JpaRepository<Request, Long> {
	@Query("SELECT a FROM RegistrationRequest a where a.status = 0")
	List<RegistrationRequest> getAllRegistrations();
	@Query("SELECT a FROM RegistrationRequest a where a.key = :key")
	List<RegistrationRequest> findByKey(@Param("key")String key);
	Request findOneById(Long id);
	@Query("SELECT a FROM RoomRequest a where a.status = 0")
	List<RoomRequest> getAllRoomRequests();
	Page<Request> findAll(Pageable pageable);
}
