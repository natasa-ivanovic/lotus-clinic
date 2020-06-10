package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.VacationRequest;

public class VacationRequestDTO {
	private long id;
	private Date startDate;
	private Date endDate;
	private String name;
	private String sureName;
	
	
	public VacationRequestDTO(VacationRequest request) {
		super();
		this.id = request.getId();
		this.startDate = request.getStartDate();
		this.endDate = request.getEndDate();
		this.name = request.getMedicalPerson().getName();
		this.sureName = request.getMedicalPerson().getSurname();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSureName() {
		return sureName;
	}
	public void setSureName(String sureName) {
		this.sureName = sureName;
	}

	
}
