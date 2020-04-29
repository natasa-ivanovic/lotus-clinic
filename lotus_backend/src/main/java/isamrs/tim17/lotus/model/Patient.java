package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Patient.java
 * Author:  Shejv
 * Purpose: Defines the Class Patient
 ***********************************************************************/

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;

	public Patient() {}
	
	public Patient(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, Date birthDate, Gender gender, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, birthDate, gender);
		this.clinic = clinic;
	}
	
}