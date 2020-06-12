package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.CalendarEntry;

public class CalendarEntryDTO {
	private Date startDate;
	private Date endDate;
	private AppointmentDTO appointment;
	private OperationDTO operation;
	private VacationDTO vacation;
	private long id;
	
	public CalendarEntryDTO() {}
	
	public CalendarEntryDTO(CalendarEntry entry) {
		this.id = entry.getId();
		this.startDate = entry.getStartDate();
		this.endDate = entry.getEndDate();
		this.appointment = entry.getAppointment() != null ? new AppointmentDTO(entry.getAppointment()) : null;
		this.operation = entry.getOperation() != null ? new OperationDTO(entry.getOperation()) : null;
		this.vacation = entry.getVacation() != null ? new VacationDTO(entry.getVacation()) : null;
	}

	public AppointmentDTO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentDTO appointment) {
		this.appointment = appointment;
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

	public VacationDTO getVacation() {
		return vacation;
	}

	public void setVacation(VacationDTO vacation) {
		this.vacation = vacation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OperationDTO getOperation() {
		return operation;
	} 

	public void setOperation(OperationDTO operation) {
		this.operation = operation;
	}
	
	
}
