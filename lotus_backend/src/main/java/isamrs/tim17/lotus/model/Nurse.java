package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Nurse.java
 * Author:  Shejv
 * Purpose: Defines the Class Nurse
 ***********************************************************************/

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	public Clinic clinic;
	
	public Nurse() {}
}