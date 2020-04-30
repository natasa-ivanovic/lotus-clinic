package isamrs.tim17.lotus.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CENTRE_ADMIN")
public class ClinicalCentreAdministrator extends User {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ClinicalCentre clinicalCentre;
	
	public ClinicalCentreAdministrator() {}
	
	public ClinicalCentreAdministrator(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, long id, Date birthDate, Gender gender, ClinicalCentre clinicalCentre) {
		super(email, password, name, surname, address, city, country, phoneNumber, id, birthDate, gender);
		this.clinicalCentre = clinicalCentre;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
	

}