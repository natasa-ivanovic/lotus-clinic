package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.List;

import isamrs.tim17.lotus.model.Clinic;

public class ClinicDTO {

	private long id;
	private String name;
	private String address;
	private String description;
	private List<DoctorDTO> doctors = new ArrayList<>();
	private double rating;
	private double price; 
	
	public ClinicDTO() {
		super();
	}
	
	public ClinicDTO(Clinic clinic) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.address = clinic.getAddress();
		this.description = clinic.getDescription();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DoctorDTO> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorDTO> doctors) {
		this.doctors = doctors;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double grade) {
		this.rating = grade;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
