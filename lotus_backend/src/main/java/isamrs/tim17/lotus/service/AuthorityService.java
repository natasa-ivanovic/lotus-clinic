package isamrs.tim17.lotus.service;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.repository.AuthorityRepository;

public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authority;
	
	/*public Authority findOne(long id) {
		return authority.findById(id).orElseGet(null);
	}
	
	public Authority findByName(String id) {
		return authority.findById(id).orElseGet(null);
	}*/
}
