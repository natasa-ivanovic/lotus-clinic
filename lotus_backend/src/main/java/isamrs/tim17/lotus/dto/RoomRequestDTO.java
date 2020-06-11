package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;

public class RoomRequestDTO {
	
	private long id;
	private Date startDate;
	private UserDTO patient;
	private List<UserDTO> doctors;
	private String type;
	private boolean operation;
	
	
	public RoomRequestDTO() {
		this.doctors = new ArrayList<>();
		}
	
	public RoomRequestDTO(long id, Date date, Patient patient, Doctor doctor) {
		this();
		this.id = id;
		this.startDate = date;
		this.patient = new UserDTO(patient);
		this.doctors = new ArrayList<>();
		this.doctors.add(new UserDTO(doctor));
		this.type = doctor.getSpecialty().getType().getName();
		this.operation = false;
	}
	
	public RoomRequestDTO(long id, Date date, Patient patient, List<Doctor> doctors, String type) {
		this();
		this.id = id;
		this.startDate = date;
		this.patient = new UserDTO(patient);
		for (Doctor doctor : doctors) {
			this.doctors.add(new UserDTO(doctor));
		}
		this.type = type;
		this.operation = true;
		
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

	public List<UserDTO> getDoctors() {
		return doctors;
	}

	public void setDoctor(List<UserDTO> doctors) {
		this.doctors = doctors;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	public void setDoctors(List<UserDTO> doctors) {
		this.doctors = doctors;
	}
	
	
	
	
	
	

}
