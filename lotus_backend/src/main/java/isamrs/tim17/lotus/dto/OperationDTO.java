package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;

public class OperationDTO {

	private Date startDate;
	private Date endDate;
	private String operationType;
	private String roomName;
	private List<String> doctors;
	private String clinicName;
	private double price;
	private PatientDTO patient;
	private OperationStatus status;
	
	public OperationDTO(Operation o) {
		this.status = o.getStatus();
		this.patient = new PatientDTO(o.getMedicalRecord().getPatient());
		this.startDate = o.getStartDate();
		this.endDate = o.getEndDate();
		this.operationType = o.getType().getName();
		this.roomName = o.getRoom().getName();
		this.clinicName = o.getClinic().getName();
		this.doctors = new ArrayList<>();
		for (Doctor d : o.getDoctor())
			doctors.add(d.getName() + " " + d.getSurname());
		this.price = o.getPrice();
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

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<String> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<String> doctors) {
		this.doctors = doctors;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}
	
	
	
	
	

}
