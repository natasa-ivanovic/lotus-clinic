package isamrs.tim17.lotus.model;

import java.util.ArrayList;

/***********************************************************************
 * Module:  RoomRequest.java
 * Author:  Shejv
 * Purpose: Defines the Class RoomRequest
 ***********************************************************************/

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ROOM")
public class RoomRequest extends Request {
	@Column(name = "date", nullable = true, unique = false)
	private Date date;
	@Column(name = "patient", nullable = true, unique = false)
	private Long patient;
	
	@Column(name="price", unique = false, nullable = false)
	private double price;
	@Column(name="discount", unique = false, nullable = false)
	private double discount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AppointmentType appType; // contains boolean operation

	@ManyToMany(mappedBy="requests", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Doctor> doctors = new HashSet<>();
	
	@Column(name = "type", nullable = true, unique = false)
	private RoomRequestType type;
	
	public RoomRequest() {}
		
	public RoomRequest(Date date, Long patient, Set<Doctor> doctors, RoomRequestType type) {
		super();
		this.date = date;
		this.patient = patient;
		this.doctors = doctors;
		this.type = type;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getPatient() {
		return patient;
	}
	public void setPatient(Long patient) {
		this.patient = patient;
	}
	public Set<Doctor> getDoctors() {
		return doctors;
	}
	public void setDoctor(Set<Doctor> doctors) {
		this.doctors = doctors;
	}
	public RoomRequestType getType() {
		return type;
	}
	public void setType(RoomRequestType type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public AppointmentType getAppType() {
		return appType;
	}

	public void setAppType(AppointmentType appType) {
		this.appType = appType;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	
	
}