package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Appointment;

public class PremadeAppDTO {
	private long id;
	private Date startDate;
	private Date endDate;
	private long doctorId;
	private String doctorName;
	private String doctorSurname;
	private long roomId;
	private String roomName;
	private String type;
	private long patientId;
	private String patientName;
	private String patientSurname;
	private String appType;
	
	
	public PremadeAppDTO() {}

	public PremadeAppDTO(Appointment app) {
		this.setId(app.getId());
		this.startDate = app.getStartDate();
		this.appType = app.getAppointmentType().getName();
		this.endDate = app.getEndDate();
		this.doctorId = app.getDoctor().getId();
		this.doctorName = app.getDoctor().getName();
		this.doctorSurname = app.getDoctor().getSurname();
		this.roomId = app.getRoom().getId();
		this.roomName = app.getRoom().getName();
		this.setType(app.getAppointmentType().getName());
		if (app.getMedicalRecord() != null) {
		this.patientName = app.getMedicalRecord().getPatient().getName();
		this.patientSurname = app.getMedicalRecord().getPatient().getSurname();
		this.patientId = app.getMedicalRecord().getId();
		}
		
	}
	
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getPatientSurname() {
		return patientSurname;
	}

	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
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

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	
}
