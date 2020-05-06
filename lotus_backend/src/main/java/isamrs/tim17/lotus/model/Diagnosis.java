package isamrs.tim17.lotus.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/***********************************************************************
 * Module:  Diagnosis.java
 * Author:  Shejv
 * Purpose: Defines the Class Diagnosis
 ***********************************************************************/

@Entity
@Table(name="diagnosis")
public class Diagnosis {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="name", unique=true, nullable=false)
	private String name;
	@ManyToOne(fetch = FetchType.EAGER)
	private ClinicalCentre clinicalCentre;
	@OneToMany(mappedBy = "diagnosis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	public Diagnosis() {}
	
	public Diagnosis(String name) {
		this.id = this.getId();
		this.name = name;
	}

	public Diagnosis(String name, ClinicalCentre clinicCentre) {
		this.id = this.getId();
		this.name = name;
		this.clinicalCentre = clinicCentre;
	}
	
	@Override
	public String toString() {
		return "Diagnosis [id=" + id + ", name=" + name + ", clinicCentre=" + clinicalCentre + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClinicalCentre getClinicalCentre() {
		return clinicalCentre;
	}

	public void setClinicalCentre(ClinicalCentre clinicalCentre) {
		this.clinicalCentre = clinicalCentre;
	}
	
	

}