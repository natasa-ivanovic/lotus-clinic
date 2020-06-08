package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.Diagnosis;
import isamrs.tim17.lotus.model.Prescription;

public class PremadeAppDTO {
	private long id;
	private Date startDate;
	private Date endDate;
	private long doctorId;
	private String doctorName;
	private String doctorSurname;
	private long roomId;
	private String roomName;
	private String type;
	private long patientId;
	private String patientName;
	private String patientSurname;
	private String clinic;
	private List<String> diagnosis;
	private List<String> recipes;
	private boolean rated;
	private String description;
	private double price;
	private double discount;
	
	public PremadeAppDTO() {
	}

	public PremadeAppDTO(Appointment app) {
		this.setId(app.getId());
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctorId = app.getDoctor().getId();
		this.doctorName = app.getDoctor().getName();
		this.doctorSurname = app.getDoctor().getSurname();
		this.roomId = app.getRoom().getId();
		this.roomName = app.getRoom().getName();
		this.price = app.getPrice();
		this.discount = app.getDiscount();
		this.setType(app.getAppointmentType().getName());
		if (app.getMedicalRecord() != null) {
			this.patientName = app.getMedicalRecord().getPatient().getName();
			this.patientSurname = app.getMedicalRecord().getPatient().getSurname();
			this.patientId = app.getMedicalRecord().getId();
		}
		
		if (!app.getPrescriptions().isEmpty()) {
			this.recipes = new ArrayList<>();
			Iterator<Prescription> it = app.getPrescriptions().iterator();
			while (it.hasNext()) {
				Prescription p = it.next();
				recipes.add(p.getMedicine().getName());
			}
		}
		
		if (!app.getDiagnosis().isEmpty()) {
			this.diagnosis = new ArrayList<>();
			Iterator<Diagnosis> it = app.getDiagnosis().iterator();
			while (it.hasNext()) {
				Diagnosis d = it.next();
				diagnosis.add(d.getName());
			}
		}
		
		this.rated = app.isReviewed();
		this.setDescription(app.getInformation());
		this.clinic = app.getClinic().getName();
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSurname() {
		return patientSurname;
	}

	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
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

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSurname() {
		return doctorSurname;
	}

	public void setDoctorSurname(String doctorSurname) {
		this.doctorSurname = doctorSurname;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public List<String> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<String> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public List<String> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<String> recipes) {
		this.recipes = recipes;
	}

	public boolean isRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
	

}
