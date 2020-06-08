package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository rooms;
	
	public Room findOne(long id) {
		return rooms.findOneById(id);
	}

	public List<Room> findAll() {
		return rooms.findAll();
	}
	
	public Room save(Room room) {
		return rooms.save(room);
	}

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
