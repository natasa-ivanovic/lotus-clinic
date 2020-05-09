package isamrs.tim17.lotus.dto;

import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;

public class DoctorDTO {
	private String name;
	private String surname;
	private double grade;	
	private List<Date> availableTimes;
	
	public DoctorDTO() {}

	public DoctorDTO(Doctor d, double grade, List<Date> times) {
		super();
		this.name = d.getName();
		this.surname = d.getSurname();
		this.grade = grade;
		this.availableTimes = times;
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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public List<Date> getAvailableTimes() {
		return availableTimes;
	}

	public void setAvailableTimes(List<Date> availableTimes) {
		this.availableTimes = availableTimes;
	}
	
	
} 
