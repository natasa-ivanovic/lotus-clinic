package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Operation.java
 * Author:  Shejv
 * Purpose: Defines the Class Operation
 ***********************************************************************/

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "operations")
public class Operation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="startDate", unique=false, nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name="endDate", unique=false, nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@Column(name="status", unique=false, nullable=false)
	private OperationStatus status;

	@OneToOne(fetch = FetchType.EAGER)
	private AppointmentPrice type;
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicalRecord medicalRecord;
	@ManyToOne(fetch = FetchType.EAGER)
	private Room room;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Doctor> doctor;
	@ManyToOne(fetch = FetchType.EAGER)
	private Clinic clinic;
	
	
	@Override
	public String toString() {
		return "Operation [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status
				+ ", type=" + type + ", medicalRecord=" + medicalRecord + ", room=" + room + ", doctor=" + doctor + "]";
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
	public OperationStatus getStatus() {
		return status;
	}
	public void setStatus(OperationStatus status) {
		this.status = status;
	}
	public AppointmentPrice getType() {
		return type;
	}
	public void setType(AppointmentPrice type) {
		this.type = type;
	}
	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Set<Doctor> getDoctor() {
		return doctor;
	}
	public void setDoctor(Set<Doctor> doctor) {
		this.doctor = doctor;
	}
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	
}