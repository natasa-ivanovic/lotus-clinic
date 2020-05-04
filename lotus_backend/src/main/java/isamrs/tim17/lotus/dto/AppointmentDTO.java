package isamrs.tim17.lotus.dto;

public class AppointmentDTO {
	
	private String startDate;
	private long endDate;
	private long doctor;
	private long appointmentType;
	private long room;
	
	public AppointmentDTO() {}
	
	public AppointmentDTO(AppointmentDTO app) {
		this.startDate = app.getStartDate();
		this.endDate = app.getEndDate();
		this.doctor = app.getDoctor();
		this.appointmentType = app.getAppointmentType();
		this.room = app.getRoom();
	}
	

	@Override
	public String toString() {
		return "AppointmentDTO [startDate=" + startDate + ", endDate=" + endDate + ", doctor=" + doctor
				+ ", appointmentType=" + appointmentType + ", room=" + room + "]";
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public long getDoctor() {
		return doctor;
	}

	public void setDoctor(long doctor) {
		this.doctor = doctor;
	}

	public long getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(long appointmentType) {
		this.appointmentType = appointmentType;
	}

	public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}

	
		
	

}
