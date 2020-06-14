package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.repository.OperationRepository;

@Service
@Transactional(readOnly = true)
public class OperationService {
	@Autowired
	OperationRepository operations;
	@Autowired
	CalendarEntryService calendarService;
	@Autowired
	RequestService requestService;
	
	public Operation findOne(long id) {
		return operations.findOneById(id);
	}

	public List<Operation> findByStatus(OperationStatus status) {

		return operations.findByStatus(status);
	}

	public List<Operation> findAll() {
		return operations.findAll();
	}
	
	@Transactional(readOnly = false)
	public Operation save(Operation app) {
		return operations.save(app);
	}

	@Transactional(readOnly = false)
	public Operation save(Operation opp, List<CalendarEntry> entries, RoomRequest rr) {
		requestService.save(rr);
		for (CalendarEntry entry : entries) {
			calendarService.save(entry);
		}
		return operations.save(opp);
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		operations.deleteById(id);
	}

	public Page<Operation> findByMedicalRecord(MedicalRecord medicalRecord, OperationStatus finished,
			Pageable paging) {
		return operations.findByMedicalRecordAndStatus(medicalRecord, finished, paging);
	}

	
	
}
