package isamrs.tim17.lotus.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.RoomDTO;
import isamrs.tim17.lotus.dto.RoomDateDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.util.DateUtil;

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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Room> addRoom(@RequestBody String name) {
		System.out.println("Adding a room...");
		System.out.println(name);
		
		/*
		 * if (isEmptyOrNull(room)) { System.out.println("Something's wrong...");
		 * return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
		 */
		Room room = new Room(name); 
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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") long id) {
		
		Room room = service.findOne(id);
		RoomDTO dto = new RoomDTO(room);
		// room must exist
		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
	/**
	 * This method is used for editing a room.
	 * 
	 * @param room This is a room object from the HTTP request.
	 * @param id     This is the id of the edited room.
	 * @return ResponseEntity This returns the HTTP status code.
	 */
	@PutMapping("/rooms/{id}")
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDTO> deleteRoom(@PathVariable("id") long id) {
		System.out.println(id);
		Room room = service.findOne(id);

		if (room == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		RoomDTO dto = new RoomDTO(room);
		System.out.println("Deleting " + room);
		service.remove(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/rooms/free")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDateDTO> getFreeRooms(@RequestBody String date) {
		System.out.println(date);
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date endDate = DateUtil.addMinutes(startDate, 30);
		System.out.println(startDate);
		System.out.println("End date: " + endDate);
		
		List<Room> rooms = service.findAll();
		List<RoomDTO> dto = new ArrayList<>();
		
		for (Room r : rooms) {
			System.out.println("-------------------");
			System.out.println(r.getName());
			boolean free = true;
			for (Appointment a : r.getAppointments()) {
				System.out.println("Zahtev => START: " + startDate + " KRAJ: " + endDate);
				System.out.println("Staroo => START: " + a.getStartDate() + " KRAJ: " + a.getEndDate());
				if(DateUtil.overlap(startDate, endDate, a.getStartDate(), a.getEndDate()) == true) {
					free = false;
				}
			}
			if (free == true) {
				dto.add(new RoomDTO(r));
			}
			System.out.println("Slobodno: " + free);
			System.out.println("***");
		}
		RoomDateDTO roomDto = new RoomDateDTO(dto, endDate.getTime());
		return new ResponseEntity<>(roomDto, HttpStatus.OK);
	}
	
}
