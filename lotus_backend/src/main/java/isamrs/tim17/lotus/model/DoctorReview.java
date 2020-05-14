package isamrs.tim17.lotus.model;

import javax.persistence.Column;

/***********************************************************************
 * Module:  DoctorReview.java
 * Author:  Shejv
 * Purpose: Defines the Class DoctorReview
 ***********************************************************************/

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctor_review")
public class DoctorReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "grade", unique = false, nullable = false)
	private float grade;

	@ManyToOne(fetch = FetchType.LAZY)
	private Doctor doctor;

	public DoctorReview() {}

	public DoctorReview(float grade, Doctor doctor) {
		this.grade = grade;
		this.doctor = doctor;
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

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	
	
	
}