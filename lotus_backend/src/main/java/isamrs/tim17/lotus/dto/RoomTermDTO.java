package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Room;

public class RoomTermDTO implements Comparable<RoomTermDTO> {

	private long id;
	private String name;
	private Date term;

	public RoomTermDTO() {
	}

	public RoomTermDTO(Room room, Date freeTerm) {
		this.id = room.getId();
		this.name = room.getName();
		this.term = freeTerm;
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

	public Date getTerm() {
		return term;
	}

	public void setTerm(Date term) {
		this.term = term;
	}

	@Override
	public int compareTo(RoomTermDTO o) {
		return getTerm().compareTo(o.getTerm());
	}

}
