package isamrs.tim17.lotus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

}
