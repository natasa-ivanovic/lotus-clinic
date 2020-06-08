package isamrs.tim17.lotus.dto;

public class RatingDTO {
	private float doctorRating;
	private float clinicRating;
	private long appointmentId;
	
	public float getDoctorRating() {
		return doctorRating;
	}
	public void setDoctorRating(float doctorRating) {
		this.doctorRating = doctorRating;
	}
	public float getClinicRating() {
		return clinicRating;
	}
	public void setClinicRating(float clinicRating) {
		this.clinicRating = clinicRating;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	
}
