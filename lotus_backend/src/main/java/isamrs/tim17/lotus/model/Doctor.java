package isamrs.tim17.lotus.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {
	//@Column(name = "speciality", unique = false, nullable = false)
	//public AppointmentType speciality;
	//@Column(name = "operation", unique = false, nullable = true)
	//public Operation operation;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	public Doctor() {}

	/*public Doctor(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, int id, UserType type, Date birthDate, Gender gender,
			AppointmentType speciality, Operation operation, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender, clinic);
		this.speciality = speciality;
		this.operation = operation;
	}*/

}