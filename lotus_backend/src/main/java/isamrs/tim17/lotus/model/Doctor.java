package isamrs.tim17.lotus.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import isamrs.tim17.lotus.dto.UserDTO;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {
	
	@OneToOne(fetch = FetchType.EAGER)
	private AppointmentPrice specialty;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Operation> operations = new HashSet<>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DoctorReview> reviews = new HashSet<>();
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;

	@Column(name="last_requested")
	private Date lastRequested;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RoomRequest> requests = new HashSet<>();
	
	public Doctor() {
		this.lastRequested = new Date();}
	public Doctor(UserDTO doctor) {
		super(doctor);		
		this.lastRequested = new Date();
	}
	
	
	public void setLastRequested(Date lastRequested) {
		this.lastRequested = lastRequested;
	}

	public AppointmentPrice getSpecialty() {
		return specialty;
	}

	public void setSpecialty(AppointmentPrice specialty) {
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

	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}


	public Set<RoomRequest> getRequests() {
		return requests;
	}

	public void setRequests(Set<RoomRequest> requests) {
		this.requests = requests;
	}

	
	 

}