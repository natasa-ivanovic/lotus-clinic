package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Appointment;

public class AppointmentDTO {
	
	private String startDateString;
	private long endDateLong;
	private long doctor;
	private long appointmentType;
	private long room;
	private UserDTO patient;
	private Date endDate;
	private Date startDate;

	public AppointmentDTO() {}
	
	public AppointmentDTO(AppointmentDTO app) {
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctor = app.getDoctor();
		this.appointmentType = app.getAppointmentType();
		this.room = app.getRoom();
	}
	
	public AppointmentDTO(Appointment app, UserDTO patient) {
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.appointmentType = app.getAppointmentType().getId();
		this.room = app.getRoom().getId();
		this.patient = patient;
		
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public long getEndDateLong() {
		return endDateLong;
	}

	public void setEndDateLong(long endDateLong) {
		this.endDateLong = endDateLong;
	}

	public long getDoctor() {
		return doctor;
	}

	public void setDoctor(long doctor) {
		this.doctor = doctor;
	}

	public long getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(long appointmentType) {
		this.appointmentType = appointmentType;
	}

	public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public UserDTO getPatient() {
		return patient;
	}

	public void setPatient(UserDTO patient) {
		this.patient = patient;
	}
	

	
		
	

}
