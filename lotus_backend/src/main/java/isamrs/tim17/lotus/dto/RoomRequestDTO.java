package isamrs.tim17.lotus.dto;

import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;

public class RoomRequestDTO {
	
	private long id;
	private Date startDate;
	private UserDTO patient;
	//private UserDTO doctor;
	private List<UserDTO> doctors;
	private String type;
	
	public RoomRequestDTO() {}
	
	public RoomRequestDTO(long id, Date date, Patient patient, Doctor doctor) {
		this.id = id;
		this.startDate = date;
		this.patient = new UserDTO(patient);
		this.doctors.add(new UserDTO(doctor));
		this.type = doctor.getSpecialty().getType().getName();
	}
	
	public RoomRequestDTO(long id, Date date, Patient patient, List<Doctor> doctors) {
		this.id = id;
		this.startDate = date;
		this.patient = new UserDTO(patient);
		for (Doctor doctor : doctors) {
			this.doctors.add(new UserDTO(doctor));
		}
		//this.type = doctor.getSpecialty().getType().getName();
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
	
	
	
	
	
	

}
