package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.repository.CalendarEntryRepository;

@Service
public class CalendarEntryService {

	@Autowired
	private CalendarEntryRepository calendarEntries;
	
	public List<CalendarEntry> findByMedicalPersonId(User u) {
		return calendarEntries.findBymedicalPerson(u);
	}
	
	public CalendarEntry save(CalendarEntry entry) {
		return calendarEntries.save(entry);
	}
}
