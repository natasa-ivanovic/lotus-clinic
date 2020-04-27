package isamrs.tim17.lotus.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.RoomDTO;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.service.RoomService;

@RestController
@RequestMapping("/api")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	/**
	 * This method is used for adding a room.
	 * 
	 * @param room This is a doctor object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PostMapping("/rooms")
	public ResponseEntity<Room> addRoom(@RequestBody Room room) {
		System.out.println("Adding a room...");
		System.out.println(room);
		
		/*
		 * if (isEmptyOrNull(room)) { System.out.println("Something's wrong...");
		 * return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
		 */
		
		service.save(room);
		System.out.println("Database is ok...");
		return new ResponseEntity<>(room, HttpStatus.OK);
	}
	
	/**
	 * This method is used for getting a list of rooms.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of rooms and the HTTP status
	 *         code.
	 */
	@GetMapping("/rooms")
	public ResponseEntity<List<RoomDTO>> getAllRooms() {
		List<Room> rooms = service.findAll();
		
		// convert rooms to DTOs
		List<RoomDTO> roomsDTO = new ArrayList<>();
		for (Room r : rooms) {
			roomsDTO.add(new RoomDTO(r));
		}
		return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used for getting a room.
	 * 
	 * @param id This is requested room's id.
	 * @return Room This returns the requested room.
	 */
	@GetMapping("/rooms/{id}")
	public ResponseEntity<Room> getRoom(@PathVariable("id") long id) {
		
		Room room = service.findOne(id);

		// room must exist
		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(room, HttpStatus.OK);

	}
	
	/**
	 * This method is used for editing a room.
	 * 
	 * @param room This is a room object from the HTTP request.
	 * @param id     This is the id of the edited room.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/rooms/{id}")
	public ResponseEntity<Room> updateRoom(@RequestBody Room newRoom, @PathVariable long id) {
		
		//TODO VALIDATION!
		
		if (id != newRoom.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// a room must exist
		Room room = service.findOne(newRoom.getId());

		if (room == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		room.setName(newRoom.getName());
		room = service.save(room);
		return new ResponseEntity<>(room, HttpStatus.OK);

	}
	
	/**
	 * This method is used for deleting a room.
	 * 
	 * @param id This is the id of the deleted room.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") long id) {
		System.out.println(id);
		Room room = service.findOne(id);

		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		System.out.println("Deleting " + room);
		service.remove(id);
		return new ResponseEntity<>(room, HttpStatus.OK);
	}


}
