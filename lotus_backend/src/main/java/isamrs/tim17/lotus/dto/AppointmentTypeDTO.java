package isamrs.tim17.lotus.dto;

import java.util.HashSet;
import java.util.Set;

import isamrs.tim17.lotus.model.AppointmentType;

public class AppointmentTypeDTO {
	
	private long id;
	private String name;
	private double price;
	private double discount;
	private boolean operation;
	private Set<UserDTO> doctors = new HashSet<>();
	
	public AppointmentTypeDTO() {}
	
	public AppointmentTypeDTO(AppointmentType at) {
		this.id = at.getId();
		this.name = at.getName();
		this.price = at.getPrice();
		this.discount = at.getDiscount();	
		this.setOperation(at.isOperation());
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

}
