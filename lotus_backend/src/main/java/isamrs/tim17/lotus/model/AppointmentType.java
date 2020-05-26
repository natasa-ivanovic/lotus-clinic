package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  AppointmentType.java
 * Author:  Shejv
 * Purpose: Defines the Class AppointmentType
 ***********************************************************************/

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="appointmentTypes")
public class AppointmentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name= "name", unique = false, nullable = false)
	private String name;
	private double price;
    
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@OneToMany(mappedBy = "appointmentType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	@OneToMany(mappedBy = "specialty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Doctor> doctors = new HashSet<Doctor>();
	
	public AppointmentType() {}
	
	public AppointmentType(String name, double price) {
		this.id = this.getId();
		this.name = name;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "AppointmentType [id=" + id + ", name=" + name + ", clinic=" + clinic + "]";
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

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}