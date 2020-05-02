package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Patient.java
 * Author:  Shejv
 * Purpose: Defines the Class Patient
 ***********************************************************************/

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@OneToOne(targetEntity=MedicalRecord.class, cascade = CascadeType.ALL, mappedBy = "patient")
	private MedicalRecord medicalRecord;

	public Patient() {}
	
	public Patient(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, Date birthDate, Gender gender, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, birthDate, gender);
		this.clinic = clinic;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	
	
}