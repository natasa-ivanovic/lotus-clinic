package isamrs.tim17.lotus.dto;

import java.util.Date;

import isamrs.tim17.lotus.model.Vacation;


public class VacationDTO {
	public Date startDate;
	public Date endDate;
	
	public VacationDTO() {}
	
	public VacationDTO(Vacation vacation) {
		this.startDate = vacation.getStartDate();
		this.endDate = vacation.getEndDate();
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
