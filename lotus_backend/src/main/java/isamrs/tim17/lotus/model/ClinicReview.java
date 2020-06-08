package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  ClinicReview.java
 * Author:  Shejv
 * Purpose: Defines the Class ClinicReview
 ***********************************************************************/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clinic_review")
public class ClinicReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "grade", unique = false, nullable = false)
	private float grade;

	@ManyToOne(fetch = FetchType.LAZY)
	private Clinic clinic;

	
	public ClinicReview() {}
	
	public ClinicReview(float grade, Clinic clinic) {
		super();
		this.grade = grade;
		this.clinic = clinic;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


}