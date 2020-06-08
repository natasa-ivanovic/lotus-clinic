package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Gender;
import isamrs.tim17.lotus.model.User;

public class UserDTO {

	private Long id;
	private Long ssid;
	private String username;
	private String name;
	private String surname;
	private String address;
	private String password;
	private String city;
	private String country;
	private String phoneNumber;
	private Date birthDate;
	private Gender gender;

	public UserDTO() {
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.country = user.getCountry();
		this.phoneNumber = user.getPhoneNumber();
		this.birthDate = user.getBirthDate();
		this.gender = user.getGender();
		this.ssid = user.getSsid();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Long getSsid() {
		return ssid;
	}

	public void setSsid(Long ssid) {
		this.ssid = ssid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmpty() {
		return (getName() == null || "".equals(getName()) || getSurname() == null || "".equals(getSurname())
				|| getUsername() == null || "".equals(getUsername()) || getAddress() == null || "".equals(getAddress())
				|| getCity() == null || "".equals(getCity()) || getCountry() == null || "".equals(getCountry())
				|| getPhoneNumber() == null || "".equals(getPhoneNumber()) || getGender() == null
				|| "".equals(getGender().toString()) || getBirthDate() == null || "".equals(getBirthDate().toString()));
	}

}
