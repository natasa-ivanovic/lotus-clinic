package isamrs.tim17.lotus.dto;

import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;

public class DoctorDTO {
	private Long id;
	private String name;
	private String surname;
	private double rating;	
	private List<Date> availableTimes;
	private ClinicDTO clinic;
	private String type;
	
	public DoctorDTO() {}

	public DoctorDTO(Doctor d, double rating, List<Date> times) {
		super();
		this.setId(d.getId());
		this.name = d.getName();
		this.surname = d.getSurname();
		this.rating = rating;
		this.availableTimes = times;
		this.setClinic(new ClinicDTO(d.getClinic()));
		this.setType(d.getSpecialty().getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Date> getAvailableTimes() {
		return availableTimes;
	}

	public void setAvailableTimes(List<Date> availableTimes) {
		this.availableTimes = availableTimes;
	}

	public ClinicDTO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDTO clinic) {
		this.clinic = clinic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
} 
