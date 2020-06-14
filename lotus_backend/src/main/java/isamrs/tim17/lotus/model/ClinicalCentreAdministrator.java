package isamrs.tim17.lotus.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("CENTRE_ADMIN")
public class ClinicalCentreAdministrator extends User {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ClinicalCentre clinicalCentre;
	
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Request.class)
	private Set<Request> requests = new HashSet<>();
	
	public ClinicalCentreAdministrator() {}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
	

}