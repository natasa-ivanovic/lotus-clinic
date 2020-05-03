package isamrs.tim17.lotus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.repository.AuthorityRepository;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authority;
	
	public Authority findOne(long id) {
		return authority.findById(id).orElseGet(null);
	}
	
	public Authority findByName(String id) {
		return authority.findByName(id);
	}
}
