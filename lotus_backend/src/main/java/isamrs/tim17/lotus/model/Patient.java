package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Patient.java
 * Author:  Shejv
 * Purpose: Defines the Class Patient
 ***********************************************************************/

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient extends User {
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Clinic clinic;

	public Patient() {}
	
	public Patient(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, UserType type, Date birthDate, Gender gender, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender);
		this.clinic = clinic;
	}
	
}