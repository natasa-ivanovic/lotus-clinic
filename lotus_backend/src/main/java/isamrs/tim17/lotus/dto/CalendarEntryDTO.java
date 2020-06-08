package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.CalendarEntry;

public class CalendarEntryDTO {
	private Date startDate;
	private Date endDate;
	private AppointmentDTO appointment;
	//private OperationDTO operation;
	//private VacationDTO vacation;
	
	public CalendarEntryDTO() {}
	
	public CalendarEntryDTO(CalendarEntry entry) {
		this.startDate = entry.getStartDate();
		this.endDate = entry.getEndDate();
		this.appointment = entry.getAppointment() != null ? new AppointmentDTO(entry.getAppointment()) : null;
		//this.operation = 
		//this.vacation = 
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
}
