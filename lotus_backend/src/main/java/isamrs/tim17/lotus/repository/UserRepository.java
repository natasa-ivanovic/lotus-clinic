package isamrs.tim17.lotus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findOneById(Long id);
	Page<User> findAll(Pageable pageable);
	List<User> findAllBySurname(String surname);
	List<User> findByNameAndSurnameAllIgnoringCase(String name, String surname);
	User findByUsername(String username);



}
