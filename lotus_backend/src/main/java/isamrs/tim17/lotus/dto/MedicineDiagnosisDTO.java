package isamrs.tim17.lotus.dto;

import java.util.List;

public class MedicineDiagnosisDTO {
	
	private List<MedicineDTO> medicines;
	private List<DiagnosisDTO> diagnosis;
	
	public MedicineDiagnosisDTO() {}
	
	public MedicineDiagnosisDTO(List<MedicineDTO> m, List<DiagnosisDTO> d) {
		this.medicines = m;
		this.diagnosis = d;
	}

	public List<MedicineDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<MedicineDTO> medicines) {
		this.medicines = medicines;
	}

	public List<DiagnosisDTO> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<DiagnosisDTO> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	

}
