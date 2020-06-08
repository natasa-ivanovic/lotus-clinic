package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Clinic.java
 * Author:  Shejv
 * Purpose: Defines the Class Clinic
 ***********************************************************************/

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import isamrs.tim17.lotus.dto.ClinicDTO;

@Entity
@Table(name = "clinics")
public class Clinic {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, unique = false)
	private String name;
	@Column(name = "address", nullable = false, unique = false)
	private String address;
	@Column(name = "description", nullable = false, unique = false)
	private String description;
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Doctor.class)
	private Set<Doctor> doctors = new HashSet<>();
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Nurse.class)
	private Set<Nurse> nurses = new HashSet<>();
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = ClinicAdministrator.class)
	private Set<ClinicAdministrator> clinicAdministrators = new HashSet<>();

	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	private ClinicalCentre clinicalCentre;
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AppointmentPrice> pricelist = new HashSet<>();


	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER)
	private Set<ClinicReview> reviews = new HashSet<>();
	
	
	public Clinic() {}
	
	public Clinic(String name, String address, String description) {
		this.name = name;
		this.address = address;
		this.description = description;
	}

	public Clinic(ClinicDTO clinic) {
		this.name = clinic.getName();
		this.address = clinic.getAddress();
		this.description = clinic.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Set<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(Set<Nurse> nurses) {
		this.nurses = nurses;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}

	public Set<ClinicAdministrator> getClinicAdministrators() {
		return clinicAdministrators;
	}

	public void setClinicAdministrators(Set<ClinicAdministrator> clinicAdministrators) {
		this.clinicAdministrators = clinicAdministrators;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<AppointmentPrice> getPricelist() {
		return pricelist;
	}

	public void setPricelist(Set<AppointmentPrice> pricelist) {
		this.pricelist = pricelist;
	}

	public Set<ClinicReview> getReviews() {
		return reviews;
	}

	public void setReviews(Set<ClinicReview> reviews) {
		this.reviews = reviews;
	}
	
	

}