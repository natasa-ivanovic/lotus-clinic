package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.Room;

public class RoomDTO {
	
	private long id;
	private String name;
	
	public RoomDTO() {}
	
	public RoomDTO(Room room) {
		this.id = room.getId();
		this.name = room.getName();
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
