package isamrs.tim17.lotus.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "calendar", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"startDate", "room_id"}),
		@UniqueConstraint(columnNames = {"startDate", "medical_person_id"})})
public class CalendarEntry {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "startDate", nullable = false, unique = false)
	private Date startDate;
	@Column(name = "endDate", nullable = false, unique = false)
	private Date endDate;
	@ManyToOne(fetch = FetchType.LAZY)
	private User medicalPerson;
	@OneToOne(fetch = FetchType.EAGER)
	private Appointment appointment;
	@ManyToOne(fetch = FetchType.EAGER)
	private Operation operation;
	@ManyToOne(fetch = FetchType.LAZY)
	private Vacation vacation; 
	@ManyToOne(fetch = FetchType.LAZY)
	private Room room;
	
	public CalendarEntry() {}
	
	public CalendarEntry(Appointment app) {
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.medicalPerson = app.getDoctor();
		this.appointment = app;
		this.room = app.getRoom();
	}
	
	public CalendarEntry(Vacation vacation) {
		this.startDate = vacation.getStartDate();
		this.endDate = vacation.getEndDate();
		this.medicalPerson = vacation.getMedicalPerson();
		this.vacation = vacation;
		this.room = null;
	}
	
	public CalendarEntry(Operation operation, Doctor doctor) {
		this.startDate = operation.getStartDate();
		this.endDate = operation.getEndDate();
		this.medicalPerson = doctor;
		this.operation = operation;
		this.room = operation.getRoom();
	}
  
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Vacation getVacation() {
		return vacation;
	}
	public void setVacation(Vacation vacation) {
		this.vacation = vacation;
	}
	
	
}