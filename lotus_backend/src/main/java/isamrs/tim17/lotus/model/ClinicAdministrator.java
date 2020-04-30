package isamrs.tim17.lotus.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ADMIN")
public class ClinicAdministrator extends User {
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;
	
	private ClinicAdministrator(String email, String password, String name, String surname, String address, String city, String country,
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
	

}