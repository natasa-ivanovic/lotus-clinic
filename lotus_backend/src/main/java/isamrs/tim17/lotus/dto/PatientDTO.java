package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.Patient;

public class PatientDTO {
	private UserDTO patient;
	private MedicalRecordDTO record;
	
	public PatientDTO() {}
	
	public PatientDTO(Patient p) {
		this.patient = new UserDTO(p);
		this.record = new MedicalRecordDTO(p.getMedicalRecord());
	}
	
	public UserDTO getPatient() {
		return patient;
	}
	public void setPatient(UserDTO patient) {
		this.patient = patient;
	}
	public MedicalRecordDTO getRecord() {
		return record;
	}
	public void setRecord(MedicalRecordDTO record) {
		this.record = record;
	}	
	
}
