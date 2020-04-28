package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  AppointmentType.java
 * Author:  Shejv
 * Purpose: Defines the Class AppointmentType
 ***********************************************************************/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AppointmentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name= "name", unique = false, nullable = false)
	private String name;
    
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	public AppointmentType() {}
	
	public AppointmentType(String name) {
		this.id = this.getId();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "AppointmentType [id=" + id + ", name=" + name + ", clinic=" + clinic + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}