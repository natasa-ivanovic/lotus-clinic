package isamrs.tim17.lotus.model;

import javax.persistence.Column;

/***********************************************************************
 * Module:  Room.java
 * Author:  Shejv
 * Purpose: Defines the Class Room
 ***********************************************************************/

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
	@Id
	private int id;
	@Column(name= "name", unique = false, nullable = false)
	private String name;

	// public Clinic clinic;

	public Room() {
	}

	public Room(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public boolean isEmptyOrNull() {
		if (this.getName() == null || "".equals(this.getName()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "Room [name=" + name + ", id=" + id + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}