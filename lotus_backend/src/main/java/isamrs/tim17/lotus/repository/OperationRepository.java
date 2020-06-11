package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;

public interface OperationRepository extends JpaRepository<Operation, Long> {
	
	
	Operation findOneById(Long id);
	Page<Operation> findAll(Pageable pageable);
	List<Operation> findByStatus(OperationStatus status);
	List<Operation> findByMedicalRecord(MedicalRecord id);
	Page<Operation> findByMedicalRecordAndStatus(MedicalRecord medicalRecord, OperationStatus status,
			Pageable paging);
}
