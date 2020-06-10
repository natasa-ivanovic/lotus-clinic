package isamrs.tim17.lotus.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vacation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date startDate;
	private Date endDate;
	@ManyToOne(fetch = FetchType.LAZY)
	private User medicalPerson;
	
	
	public Vacation() {
		super();
	}
	
	public Vacation(VacationRequest request) {
		this.startDate = request.getStartDate();
		this.endDate = request.getEndDate();
		this.medicalPerson = request.getMedicalPerson();
	}
	
	public Vacation(Date startDate, Date endDate, User medicalPerson) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.medicalPerson = medicalPerson;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public User getMedicalPerson() {
		return medicalPerson;
	}
	public void setMedicalPerson(User medicalPerson) {
		this.medicalPerson = medicalPerson;
	}
	
	

}
