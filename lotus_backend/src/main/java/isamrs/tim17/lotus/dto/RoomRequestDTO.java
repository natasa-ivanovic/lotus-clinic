package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.RoomRequest;

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
	
	public RoomRequestDTO(RoomRequest request) {
		this();
		this.id = request.getId();
		this.startDate = request.getDate();
		this.patient = new UserDTO(request.getPatient());
		this.doctors = new ArrayList<>();
		for(Doctor doctor : request.getDoctors()) {
			doctors.add(new UserDTO(doctor));
		}
		this.type = request.getAppType().getName();
		this.operation = request.getAppType().isOperation();
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
