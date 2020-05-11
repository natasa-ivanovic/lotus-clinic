package isamrs.tim17.lotus.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.tim17.lotus.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long>{
	
	Request findOneById(String id);
	Page<Request> findAll(Pageable pageable);

}