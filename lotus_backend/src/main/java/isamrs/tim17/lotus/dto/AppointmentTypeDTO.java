package isamrs.tim17.lotus.dto;

import java.util.HashSet;
import java.util.Set;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Doctor;

public class AppointmentTypeDTO {
	
	private long id;
	private String name;
	private Set<UserDTO> doctors = new HashSet<UserDTO>();
	
	public AppointmentTypeDTO() {}
	
	public AppointmentTypeDTO(AppointmentType at) {
		this.id = at.getId();
		this.name = at.getName();
		for(Doctor d : at.getDoctors()) {
			doctors.add(new UserDTO(d));
		}		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<UserDTO> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<UserDTO> doctors) {
		this.doctors = doctors;
	}
	
	
	
	
	

}
