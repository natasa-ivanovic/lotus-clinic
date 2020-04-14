package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.repository.RoomRepository;

public class RoomService {
	
	@Autowired
	private RoomRepository rooms;
	
	public Room findOne(int id) {
		return rooms.findById(id).orElseGet(null);
	}

	public List<Room> findAll() {
		return rooms.findAll();
	}
	
	public Room save(Room room) {
		return rooms.save(room);
	}

	public void remove(int id) {
		rooms.deleteById(id);
	}

}
