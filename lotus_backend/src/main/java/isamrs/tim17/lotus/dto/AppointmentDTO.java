package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.MedicalRecord;

public class AppointmentDTO {
	
	private long appId;
	private long endDateLong;
	private long doctor;
	private long appointmentType;
	private long room;
	private Date endDate;
	private Date startDate;
	private String startDateString;
	private double price;
	private double discount;
	private PatientDTO patient;
	private String roomName;

	public AppointmentDTO() {}
	
	public AppointmentDTO(AppointmentDTO app) {
		this.appId = app.getAppId();
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctor = app.getDoctor();
		this.appointmentType = app.getAppointmentType();
		this.room = app.getRoom();
		this.price = app.price;
		this.discount = app.discount;
	}
	
	public AppointmentDTO(Appointment app) {
		this.appId = app.getId();
		MedicalRecord record = app.getMedicalRecord();
		if(record != null) {
			this.patient = new PatientDTO(app.getMedicalRecord().getPatient());
		}
		this.roomName = app.getRoom().getName();
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
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

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}
	
	

}
