package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Gender;
import isamrs.tim17.lotus.model.User;

public class UserDTO {
	
	private long id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;
	private Date birthDate;
	private Gender gender;
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		this.id = user.getId(); 
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.name =	user.getName(); 
		this.surname = user.getSurname(); 
		this.address = user.getAddress();
		this.city =	user.getCity();
		this.country = user.getCountry();
		this.phoneNumber = user.getPhoneNumber();
		this.birthDate = user.getBirthDate();
		this.gender = user.getGender();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	
}
