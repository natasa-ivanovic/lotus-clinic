package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	Room findOneById(Long id);
	List<Room> findByNameAllIgnoringCase(String name);
	Room findOneByNameIgnoringCase(String name);
	Page<Room> findAll(Pageable pageable);
	
	@Query("SELECT a FROM Room a where a.clinic = :id")
	List<Room> findAllByClinic(Clinic id);
	Page<Room> findAllByClinic(Clinic clinic, Pageable paging);
}
