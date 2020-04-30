package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Nurse.java
 * Author:  Shejv
 * Purpose: Defines the Class Nurse
 ***********************************************************************/

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends User {
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Prescription> prescriptions = new HashSet<Prescription>();
	
	public Nurse() {}
}