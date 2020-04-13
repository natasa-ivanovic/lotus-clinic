package isamrs.tim17.lotus.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor extends MedicalStaff {
	//@Column(name = "speciality", unique = false, nullable = false)
	//public AppointmentType speciality;
	//@Column(name = "operation", unique = false, nullable = true)
	//public Operation operation;

	public Doctor() {}

	/*public Doctor(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, int id, UserType type, Date birthDate, Gender gender,
			AppointmentType speciality, Operation operation, Clinic clinic) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, type, birthDate, gender, clinic);
		this.speciality = speciality;
		this.operation = operation;
	}*/

}