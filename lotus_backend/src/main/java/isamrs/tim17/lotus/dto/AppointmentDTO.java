package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Appointment;

public class AppointmentDTO {
	
	private Date startDate;
	private Date endDate;
	private long doctorId;
	private long medicalRecordId;
	private long roomId;

	
	public AppointmentDTO() {}
	
	public AppointmentDTO(Appointment app) {
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctorId = app.getDoctor().getId();
		this.medicalRecordId = app.getMedicalRecord().getId();
		this.roomId = app.getRoom().getId();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
	

}
