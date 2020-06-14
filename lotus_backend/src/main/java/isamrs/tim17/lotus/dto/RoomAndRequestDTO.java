package isamrs.tim17.lotus.dto;

public class RoomAndRequestDTO {
	
	private long room;
	private long request;
	private long startDate;
	private boolean operation;
	
	public RoomAndRequestDTO() {}
	
	public RoomAndRequestDTO(RoomAndRequestDTO rr) {
		this.room = rr.getRoom();
		this.request = rr.getRequest();
		this.startDate = rr.getStartDate();
		this.operation = rr.isOperation();
	}

	public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}

	public long getRequest() {
		return request;
	}

	public void setRequest(long request) {
		this.request = request;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	
	
}
