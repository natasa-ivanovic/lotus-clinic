package isamrs.tim17.lotus.model;

/***********************************************************************
 * Module:  RoomRequest.java
 * Author:  Shejv
 * Purpose: Defines the Class RoomRequest
 ***********************************************************************/

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROOM")
public class RoomRequest extends Request {
	@Column(name = "date", nullable = false, unique = false)
	private Date date;
	@Column(name = "patient", nullable = false, unique = false)
	private Long patient;
	@Column(name = "doctor", nullable = false, unique = false)
	private Long doctor;
	@Column(name = "type", nullable = false, unique = false)
	private RoomRequestType type;
	
	public RoomRequest() {}
		
	public RoomRequest(Date date, Long patient, Long doctor, RoomRequestType type) {
		super();
		this.date = date;
		this.patient = patient;
		this.doctor = doctor;
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
	public Long getDoctor() {
		return doctor;
	}
	public void setDoctor(Long doctor) {
		this.doctor = doctor;
	}
	public RoomRequestType getType() {
		return type;
	}
	public void setType(RoomRequestType type) {
		this.type = type;
	}
	
	
}