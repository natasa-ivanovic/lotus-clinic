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

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {
	
	@OneToOne(fetch = FetchType.EAGER)
	private AppointmentType specialty;
	//@Column(name = "operation", unique = false, nullable = true)
	//public Operation operation;
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Appointment> appointments = new HashSet<Appointment>();
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	public Doctor() {}

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

	/*public Doctor(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, int id, UserType type, Date birthDate, Gender gender,
			AppointmentType speciality, Operation operation, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender, clinic);
		this.speciality = speciality;
		this.operation = operation;
	}*/
	
	

}