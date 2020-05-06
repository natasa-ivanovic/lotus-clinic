package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.Diagnosis;

public class DiagnosisDTO {
	private long id;
	private String name;
	
	public DiagnosisDTO() {
		super();
	}
	
	public DiagnosisDTO(Diagnosis diagnosis) {
		this.id = diagnosis.getId();
		this.name = diagnosis.getName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
