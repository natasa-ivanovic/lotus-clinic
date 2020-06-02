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
	//@Column(name = "operation", unique = false, nullable = true)
	//public Operation operation;
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
	private Set<DoctorReview> reviews = new HashSet<DoctorReview>();
	
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

	/*public Doctor(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, int id, UserType type, Date birthDate, Gender gender,
			AppointmentType speciality, Operation operation, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender, clinic);
		this.speciality = speciality;
		this.operation = operation;
	}*/
	
	 

}