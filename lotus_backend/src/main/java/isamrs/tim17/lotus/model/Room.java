package isamrs.tim17.lotus.model;

import java.util.Date;
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "rooms")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name= "name", unique = false, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	//@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Operation> operations = new HashSet<Operation>();
	
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	@Column(name="lastRequested", unique = false, nullable = false)
	private Date lastRequested;
	
	@Version
	private long version;
	 
	public Room() {
		this.lastRequested = new Date();
	}
	
	public Room(String name) {
		this();
		this.name = name;
	}

	public Room(String name, Long id) {
		this();
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
	
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Date getLastRequested() {
		return lastRequested;
	}

	public void setLastRequested(Date lastRequested) {
		this.lastRequested = lastRequested;
	}
	

	

}