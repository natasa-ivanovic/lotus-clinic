package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  ClinicalCentre.java
 * Author:  Shejv
 * Purpose: Defines the Class ClinicalCentre
 ***********************************************************************/

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clinicalCentres")
public class ClinicalCentre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "clinicalCentre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ClinicalCentreAdministrator> clinicalCentreAdministrators = new HashSet<ClinicalCentreAdministrator>();
	@Column(name="name", unique = false, nullable = false)
	private String name;
	@OneToMany(mappedBy = "clinicalCentre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Clinic> clinics = new HashSet<Clinic>();
	@OneToMany(mappedBy = "clinicalCentre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Diagnosis> diagnosis = new HashSet<Diagnosis>();
	@OneToMany(mappedBy = "clinicalCentre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Medicine> medicines = new HashSet<Medicine>();
	
	public ClinicalCentre() {}
	
	@Override
	public String toString() {
		return "ClinicalCentre [id=" + id + ", administrator=" + clinicalCentreAdministrators + ", name=" + name + ", clinics="
				+ clinics + ", diagnosis=" + diagnosis + ", medicines=" + medicines + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Set<ClinicalCentreAdministrator> getClinicalCentreAdministrators() {
		return clinicalCentreAdministrators;
	}

	public void setClinicalCentreAdministrators(Set<ClinicalCentreAdministrator> clinicalCentreAdministrators) {
		this.clinicalCentreAdministrators = clinicalCentreAdministrators;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}

	public Set<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Set<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}
	
	
	
	
	
	
}