package isamrs.tim17.lotus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String id);
	

}
