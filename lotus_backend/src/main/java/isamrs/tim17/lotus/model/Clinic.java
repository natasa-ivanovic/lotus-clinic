package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Clinic.java
 * Author:  Shejv
 * Purpose: Defines the Class Clinic
 ***********************************************************************/

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clinics")
public class Clinic {
	@Id
	private int id;
	@Column(name = "name", nullable = false, unique = false)
	private String name;
	@Column(name = "address", nullable = false, unique = false)
	private String address;
	@Column(name = "description", nullable = false, unique = false)
	private String description;
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Patient> patients = new HashSet<Patient>();

	//TODO
	//	public ClinicalCentre clinicCentreId;

}