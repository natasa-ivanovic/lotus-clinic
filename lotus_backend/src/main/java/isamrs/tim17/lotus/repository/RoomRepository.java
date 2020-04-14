package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	
	Room findOneById(String id);
	List<Room> findByNameAllIgnoringCase(String name);
	Page<Room> findAll(Pageable pageable);
}
