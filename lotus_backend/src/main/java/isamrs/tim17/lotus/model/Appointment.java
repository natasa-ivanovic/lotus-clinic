package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Appointment.java
 * Author:  Shejv
 * Purpose: Defines the Class Appointment
 ***********************************************************************/

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "appointments")
public class Appointment {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="information", unique=false, nullable=true)
	private String information;
	@Column(name="startDate", unique=false, nullable=false)
	private Date startDate;
	@Column(name="endDate", unique=false, nullable=false)
	private Date endDate;
	@Column(name="status", unique=false, nullable=false)
	private AppointmentStatus status;
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicalRecord medicalRecord;
	@ManyToOne(fetch = FetchType.EAGER)
	private Doctor doctor;
	@ManyToOne(fetch = FetchType.EAGER)
	private Room room;
	@OneToOne(fetch = FetchType.EAGER)
	private AppointmentType appointmentType;
	@OneToOne(fetch = FetchType.EAGER)
	private Diagnosis diagnosis;
	@OneToMany(mappedBy="appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Prescription> prescriptions = new HashSet<Prescription>();
	
	public Appointment() {}

	public Appointment(String information, Date startDate, Date endDate, AppointmentStatus status,
			MedicalRecord medicalRecord, Doctor doctor, Room room, AppointmentType appointmentType, Diagnosis diagnosis) {
		this.id = this.getId();
		this.information = information;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.medicalRecord = medicalRecord;
		this.doctor = doctor;
		this.room = room;
		this.appointmentType = appointmentType;
		this.diagnosis = diagnosis;
	}
	
	public Appointment(Date startDate, Date endDate, AppointmentType type, Doctor doctor, Room room) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.appointmentType = type;
		this.doctor = doctor;
		this.room = room;
		this.status = AppointmentStatus.PREMADE; //IZMENITI
	}
	
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", information=" + information + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + ", patient=" + medicalRecord + ", doctor=" + doctor + ", room=" + room
				+ ", type=" + appointmentType + ", diagnosis=" + diagnosis + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
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

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Diagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
	
	

}