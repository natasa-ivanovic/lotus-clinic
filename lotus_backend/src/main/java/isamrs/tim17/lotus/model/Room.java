package isamrs.tim17.lotus.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

/***********************************************************************
 * Module:  Room.java
 * Author:  Shejv
 * Purpose: Defines the Class Room
 ***********************************************************************/

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
	@Id
	private Long id;
	@Column(name= "name", unique = false, nullable = false)
	private String name;

	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//public Clinic clinic;
	
	//@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Operation> operations = new HashSet<Operation>();
	
	//@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Appointment> appointments = new HashSet<Appointment>();

	public Room() {
	}

	public Room(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}