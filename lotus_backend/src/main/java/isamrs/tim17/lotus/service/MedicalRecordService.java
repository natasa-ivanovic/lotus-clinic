package isamrs.tim17.lotus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecords;
	
	public MedicalRecord findOne(long id) {
		return medicalRecords.findById(id).orElseGet(null);
	}

	
	public MedicalRecord save(MedicalRecord doctor) {
		return medicalRecords.save(doctor);
	}

	public void remove(long id) {
		medicalRecords.deleteById(id);
	}
	


}
