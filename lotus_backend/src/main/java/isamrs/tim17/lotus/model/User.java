package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  User.java
 * Author:  Shejv
 * Purpose: Defines the Class User
 ***********************************************************************/

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@Inheritance(
		strategy = InheritanceType.TABLE_PER_CLASS
		)
public abstract class User {
	@Id
	private long id;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@Column(name = "password", unique = false, nullable = false)
	private String password;
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	@Column(name = "surname", unique = false, nullable = false)
	private String surname;
	@Column(name = "address", unique = false, nullable = false)
	private String address;
	@Column(name = "city", unique = false, nullable = false)
	private String city;
	@Column(name = "country", unique = false, nullable = false)
	private String country;
	@Column(name = "phoneNumber", unique = false, nullable = false)
	private String phoneNumber;
	@Column(name = "birthDate", unique = false, nullable = false)
	private Date birthDate;
	@Column(name = "gender", unique = false, nullable = false)
	private Gender gender;
	
	public User() {}
	
	public User(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, UserType type, Date birthDate, Gender gender) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.birthDate = birthDate;
		this.gender = gender;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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