package isamrs.tim17.lotus.dto;

import java.util.List;

public class RoomDateDTO {
	
	private List<RoomDTO> rooms;
	private long endDate;
	
	public RoomDateDTO() {}
	
	public RoomDateDTO(List<RoomDTO> rooms, long endDate) {
		this.rooms = rooms;
		this.endDate = endDate;
	}

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomDTO> rooms) {
		this.rooms = rooms;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	

}
