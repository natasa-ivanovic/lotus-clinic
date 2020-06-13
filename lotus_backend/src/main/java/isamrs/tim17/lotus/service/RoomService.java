package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.repository.RoomRepository;

@Service
@Transactional(readOnly = true)
public class RoomService {

	@Autowired
	private RoomRepository rooms;

	public Room findOne(long id) {
		return rooms.findOneById(id);
	}

	public List<Room> findAll() {
		return rooms.findAll();
	}

	@Transactional(readOnly = false)
	public Room save(Room room) {
		return rooms.save(room);
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		rooms.deleteById(id);
	}

	public Room findOne(String name) {
		return rooms.findOneByNameIgnoringCase(name);
	}

	public List<Room> findByClinic(Clinic id) {
		return rooms.findAllByClinic(id);
	}
	
	

}
