package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  RegistrationRequest.java
 * Author:  Shejv
 * Purpose: Defines the Class RegistrationRequest
 ***********************************************************************/

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("REGISTRATION")
public class RegistrationRequest extends Request {

	@OneToOne(fetch = FetchType.LAZY)
	private Patient patient;

	public RegistrationRequest() {
		super();
	}

	public RegistrationRequest(Patient patient) {
		super();
		this.patient = patient;
		this.setStatus(RequestStatus.PENDING);
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	

}