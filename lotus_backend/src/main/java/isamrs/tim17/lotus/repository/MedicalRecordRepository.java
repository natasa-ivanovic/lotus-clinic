package isamrs.tim17.lotus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
	
	MedicalRecord findOneById(Long id);

}
