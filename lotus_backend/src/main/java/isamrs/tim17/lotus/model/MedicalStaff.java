package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  MedicalStaff.java
 * Author:  Shejv
 * Purpose: Defines the Class MedicalStaff
 ***********************************************************************/

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public abstract class MedicalStaff extends User {
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Clinic clinic;

	public MedicalStaff() {}
	
	public MedicalStaff(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, int id, UserType type, Date birthDate, Gender gender, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender);
		this.clinic = clinic;
	}
	

}