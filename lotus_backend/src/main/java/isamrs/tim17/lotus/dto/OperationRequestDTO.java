package isamrs.tim17.lotus.dto;

import java.util.Date;
import java.util.List;

public class OperationRequestDTO {
	
	private Date startDate;
	private List<Long> doctorsId;
	
	public OperationRequestDTO() {}
	
	public OperationRequestDTO(Date startDate, List<Long> doctorsId) {
		this.startDate = startDate;
		this.doctorsId = doctorsId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Long> getDoctorsId() {
		return doctorsId;
	}

	public void setDoctorsId(List<Long> doctorsId) {
		this.doctorsId = doctorsId;
	}
	
	
	
	

}
