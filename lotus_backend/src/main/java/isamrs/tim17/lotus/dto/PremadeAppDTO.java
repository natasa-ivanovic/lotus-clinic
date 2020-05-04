package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Appointment;

public class PremadeAppDTO {
	private Long id;
	private Date startDate;
	private Date endDate;
	private long doctorId;
	private String doctorName;
	private String doctorSurname;
	private long roomId;
	private String roomName;
	private String type;
	private String PatientName;
	
	
	public PremadeAppDTO() {}

	public PremadeAppDTO(Appointment app) {
		this.setId(app.getId());
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctorId = app.getDoctor().getId();
		this.doctorName = app.getDoctor().getName();
		this.doctorSurname = app.getDoctor().getSurname();
		this.roomId = app.getRoom().getId();
		this.roomName = app.getRoom().getName();
		this.setType(app.getAppointmentType().getName());
	}
	
	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
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

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSurname() {
		return doctorSurname;
	}

	public void setDoctorSurname(String doctorSurname) {
		this.doctorSurname = doctorSurname;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
