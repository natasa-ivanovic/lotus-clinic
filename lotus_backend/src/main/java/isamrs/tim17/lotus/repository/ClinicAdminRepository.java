package isamrs.tim17.lotus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.ClinicAdministrator;


public interface ClinicAdminRepository extends JpaRepository<ClinicAdministrator, Long> {

	ClinicAdministrator findOneById(String id);
	boolean existsByUsername(String username);
}
