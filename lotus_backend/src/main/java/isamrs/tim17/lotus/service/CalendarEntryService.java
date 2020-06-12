package isamrs.tim17.lotus.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.repository.CalendarEntryRepository;

@Service
public class CalendarEntryService {

	@Autowired
	private CalendarEntryRepository calendarEntries;
	
	@Autowired
	private OperationService operationService;
	
	public CalendarEntry findOne(long id) {
		return calendarEntries.findOneById(id);
	}
	
	public List<CalendarEntry> findByMedicalPersonId(User u) {
		return calendarEntries.findBymedicalPerson(u);
	}
	
	public CalendarEntry save(CalendarEntry entry) {
		return calendarEntries.save(entry);
	}
	

	public boolean remove(Appointment a) {
		try {
			CalendarEntry c = calendarEntries.findOneByAppointment(a);
			calendarEntries.deleteById(c.getId());			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<CalendarEntry> findByMedicalPersonAndDate(User u, Date startDate, Date endDate) {
		return calendarEntries.getAllBetweenDatesForDoctor(u, startDate, endDate);
	}

	public List<CalendarEntry> findByRoomAndDate(Room r, Date d, Date endDate) {
		return calendarEntries.getAllBetweenDatesForRoom(r, d, endDate);
	}

	public void cancel(long id) {
		Operation operation = operationService.findOne(id);
		Set<Doctor> doctors = operation.getDoctor();
		for(Doctor d : doctors) {
			// pronaci za svakog doktora calendar entry i skloniti
			CalendarEntry ce = calendarEntries.findOneByOperationAndMedicalPerson(operation, d);
			calendarEntries.deleteById(ce.getId());
		}
		operation.setStatus(OperationStatus.CANCELED);
		operationService.save(operation);
	}
}
