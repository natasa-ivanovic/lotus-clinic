package isamrs.tim17.lotus.dto;

import java.util.HashSet;
import java.util.Set;

import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.Doctor;

public class AppointmentPriceDTO {
	private long id;
	private String name;
	private long type_id;
	private double price;
	private double discount;
	private Set<UserDTO> doctors = new HashSet<UserDTO>();
	
	public AppointmentPriceDTO() {
		super();
	}

	public AppointmentPriceDTO(AppointmentPrice ap) {
		super();
		this.id = ap.getId();
		this.name = ap.getType().getName();
		this.type_id = ap.getType().getId();
		this.price = ap.getPrice();
		this.discount = ap.getDiscount();
		for(Doctor d : ap.getDoctors()) {
			doctors.add(new UserDTO(d));
		}	
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discoutn) {
		this.discount = discoutn;
	}

	public long getType_id() {
		return type_id;
	}

	public void setType_id(long type_id) {
		this.type_id = type_id;
	}

	public Set<UserDTO> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<UserDTO> doctors) {
		this.doctors = doctors;
	}
}
