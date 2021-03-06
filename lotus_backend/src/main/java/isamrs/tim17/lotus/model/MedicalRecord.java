package isamrs.tim17.lotus.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/***********************************************************************
 * Module:  MedicalRecord.java
 * Author:  Shejv
 * Purpose: Defines the Class MedicalRecord
 ***********************************************************************/

@Entity
@Table(name="medicalRecords")
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="height", unique=false, nullable=false)
	private float height;
	@Column(name="weight", unique=false, nullable=false)
	private float weight;
	@ManyToMany(mappedBy = "medicalRecords", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Allergy> allergies = new HashSet<>();
	@Column(name="bloodType", unique=false, nullable=false)
	private String bloodType;
   
	@OneToOne
	@MapsId
	private Patient patient;
	
	@OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	//TODO OPERATIONS
	
	public MedicalRecord() {}

	public MedicalRecord(float height, float weight, HashSet<Allergy> allergies, String bloodType, Patient patient) {
		this.id = this.getId();
		this.height = height;
		this.weight = weight;
		this.allergies = allergies;
		this.bloodType = bloodType;
		this.patient = patient;
	}
	
	

	@Override
	public String toString() {
		return "MedicalRecord [id=" + id + ", height=" + height + ", weight=" + weight + ", allergies=" + allergies
				+ ", bloodType=" + bloodType + ", patient=" + patient + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Set<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	

}