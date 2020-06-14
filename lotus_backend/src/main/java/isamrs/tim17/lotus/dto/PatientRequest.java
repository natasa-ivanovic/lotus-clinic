package isamrs.tim17.lotus.dto;

public class PatientRequest {
	private long requestDate;
	private long appointmentType;
	private boolean clinics;
	private long doctorId;
	
	public PatientRequest() {}
	
	public PatientRequest(long requestDate, long appointmentType, boolean clinics) {
		super();
		this.requestDate = requestDate;
		this.appointmentType = appointmentType;
		this.clinics = clinics;
	}
	public long getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(long requestDate) {
		this.requestDate = requestDate;
	}
	public long getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(long appointmentType) {
		this.appointmentType = appointmentType;
	}
	public boolean isClinics() {
		return clinics;
	}
	public void setClinics(boolean clinics) {
		this.clinics = clinics;
	}

	@Override
	public String toString() {
		return "PatientRequest [requestDate=" + requestDate + ", appointmentType=" + appointmentType + ", clinics="
				+ clinics + "]";
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	
	
}
