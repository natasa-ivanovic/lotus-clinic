package isamrs.tim17.lotus.dto;

import java.util.List;

public class BusinessReportDTO {
	private double averageRating;
	private double averageDoctorRating;
	private List<DoctorDTO> doctorList;
	private List<CalendarEntryDTO> calendarEntries;
	
	public BusinessReportDTO() {}
	
	public BusinessReportDTO(double averageRating, double averageDoctorRating, List<DoctorDTO> doctorList,
			List<CalendarEntryDTO> calendarEntries) {
		this.averageDoctorRating = averageDoctorRating;
		this.averageRating = averageRating;
		this.doctorList = doctorList;
		this.calendarEntries = calendarEntries;
	}

	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public double getAverageDoctorRating() {
		return averageDoctorRating;
	}
	public void setAverageDoctorRating(double averageDoctorRating) {
		this.averageDoctorRating = averageDoctorRating;
	}
	public List<DoctorDTO> getDoctorList() {
		return doctorList;
	}
	public void setDoctorList(List<DoctorDTO> doctorList) {
		this.doctorList = doctorList;
	}
	public List<CalendarEntryDTO> getCalendarEntries() {
		return calendarEntries;
	}
	public void setCalendarEntries(List<CalendarEntryDTO> calendarEntries) {
		this.calendarEntries = calendarEntries;
	}
	
	
	
}
