package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.OperationStatus;

public class CalendarEntryDTO {
	private Date startDate;
	private Date endDate;
	private AppointmentDTO appointment;
	private OperationDTO operation;
	private VacationDTO vacation;
	private long id;
	private boolean start; //
	
	public CalendarEntryDTO() {}
	
	public CalendarEntryDTO(CalendarEntry entry) {
		this.id = entry.getId();
		this.startDate = entry.getStartDate();
		this.endDate = entry.getEndDate();
		this.vacation = entry.getVacation() != null ? new VacationDTO(entry.getVacation()) : null;
		
		if (entry.getAppointment() != null) {
			this.appointment = new AppointmentDTO(entry.getAppointment());
			if (this.appointment.getStatus().equals(AppointmentStatus.SCHEDULED))
				this.start = true;
			else
				this.start = false;
		} else {
			this.appointment = null;
		}
		
		if (entry.getOperation() != null) {
			this.operation = new OperationDTO(entry.getOperation());
			if (this.operation.getStatus().equals(OperationStatus.SCHEDULED))
				this.start = true;
			else if (this.operation.getStatus().equals(OperationStatus.FINISHED))
				this.start = false;
		} else {
			this.operation = null;
		}
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

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
	
	
}
