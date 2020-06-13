package isamrs.tim17.lotus.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Operation;
import isamrs.tim17.lotus.model.OperationStatus;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.repository.CalendarEntryRepository;

@Service
@Transactional(readOnly = true)
public class CalendarEntryService {

	@Autowired
	private CalendarEntryRepository calendarEntries;

	@Autowired
	private OperationService operationService;

	@Autowired
	private AppointmentService appointmentService;

	public CalendarEntry findOne(long id) {
		return calendarEntries.findOneById(id);
	}

	public List<CalendarEntry> findByMedicalPersonId(User u) {
		return calendarEntries.findBymedicalPerson(u);
	}

	public CalendarEntry findByDateAndRoom(Room r, Date d) {
		return calendarEntries.findOneByRoomAndStartDate(r, d);
	}
	
	public List<CalendarEntry> findByRoom(Room r) {
		return calendarEntries.findAllByRoom(r);
	}

	@Transactional(readOnly = false)
	public CalendarEntry save(CalendarEntry entry) {
		return calendarEntries.save(entry);
	}

	@Transactional(readOnly = false)
	public void cancelAppointment(long appId) {
		Appointment a = appointmentService.findOne(appId);
		CalendarEntry c = calendarEntries.findOneByAppointment(a);
		calendarEntries.deleteById(c.getId());
		a.setStatus(AppointmentStatus.CANCELED);
		appointmentService.save(a);

	}

	public List<CalendarEntry> findByMedicalPersonAndDate(User u, Date startDate, Date endDate) {
		return calendarEntries.getAllBetweenDatesForDoctor(u, startDate, endDate);
	}

	public List<CalendarEntry> findByRoomAndDate(Room r, Date d, Date endDate) {
		return calendarEntries.getAllBetweenDatesForRoom(r, d, endDate);
	}
	
	//TODO ovo je cancel za operacije
	@Transactional(readOnly = false)
	public void cancel(long id) {
		Operation operation = operationService.findOne(id);
		Set<Doctor> doctors = operation.getDoctor();
		for (Doctor d : doctors) {
			// pronaci za svakog doktora calendar entry i skloniti
			CalendarEntry ce = calendarEntries.findOneByOperationAndMedicalPerson(operation, d);
			calendarEntries.deleteById(ce.getId());
		}
		operation.setStatus(OperationStatus.CANCELED);
		operationService.save(operation);
	}
}
