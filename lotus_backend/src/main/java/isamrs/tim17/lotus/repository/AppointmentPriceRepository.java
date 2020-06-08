package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.AppointmentPrice;

public interface AppointmentPriceRepository extends JpaRepository<AppointmentPrice, Long> {
	AppointmentPrice findOneById(long id);
	List<AppointmentPrice> findByClinicId(Long id);
}
