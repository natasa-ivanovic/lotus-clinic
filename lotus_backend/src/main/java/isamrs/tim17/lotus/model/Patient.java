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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import isamrs.tim17.lotus.dto.UserDTO;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	private ClinicalCentre clinicalCentre;
	
	@OneToOne(targetEntity=MedicalRecord.class, cascade = CascadeType.ALL, mappedBy = "patient")
	private MedicalRecord medicalRecord;

	public Patient() {}
	
	public Patient(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, Date birthDate, Gender gender, ClinicalCentre clinicalCentre) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, birthDate, gender);
		this.clinicalCentre = clinicalCentre;
	}


	public Patient(UserDTO p) {
		super(p);
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	
	
}