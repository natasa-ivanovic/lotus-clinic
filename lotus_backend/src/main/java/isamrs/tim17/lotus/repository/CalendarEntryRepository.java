package isamrs.tim17.lotus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.User;

public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
	CalendarEntry findOneById(Long id);
	List<CalendarEntry> findBymedicalPerson(User u);


	@Query("SELECT c FROM CalendarEntry c where c.medicalPerson = :id AND (c.startDate BETWEEN :startDate AND :endDate) order by c.startDate")
	List<CalendarEntry> getAllBetweenDatesForDoctor(@Param("id") User id, @Param("startDate") Date startDate, @Param("endDate") Date endDate); 
	
}
