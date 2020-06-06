package isamrs.tim17.lotus.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import isamrs.tim17.lotus.dto.UserDTO;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {
	
	@OneToOne(fetch = FetchType.EAGER)
	private AppointmentType specialty;

	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
	private Set<DoctorReview> reviews = new HashSet<>();
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	public Doctor() {}

	public Doctor(UserDTO doctor) {
		super(doctor);		
	}

	public AppointmentType getSpecialty() {
		return specialty;
	}

	public void setSpecialty(AppointmentType specialty) {
		this.specialty = specialty;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<DoctorReview> getReviews() {
		return reviews;
	}

	public void setReviews(Set<DoctorReview> reviews) {
		this.reviews = reviews;
	}

}