package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;

public class RoomRequestDTO {
	
	private Date startDate;
	private UserDTO patient;
	private UserDTO doctor;
	private String type;
	
	public RoomRequestDTO() {}
	
	public RoomRequestDTO(Date date, Patient patient, Doctor doctor) {
		this.startDate = date;
		this.patient = new UserDTO(patient);
		this.doctor = new UserDTO(doctor);
		this.type = doctor.getSpecialty().getName();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public UserDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(UserDTO doctor) {
		this.doctor = doctor;
	}
	
	
	
	

}
