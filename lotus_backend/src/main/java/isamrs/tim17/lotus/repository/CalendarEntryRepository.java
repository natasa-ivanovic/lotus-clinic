package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.User;

public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
	CalendarEntry findOneById(Long id);
	List<CalendarEntry> findBymedicalPerson(User u);
}
