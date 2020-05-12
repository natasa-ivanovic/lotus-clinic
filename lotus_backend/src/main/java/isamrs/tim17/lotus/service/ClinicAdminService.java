package isamrs.tim17.lotus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.repository.ClinicAdminRepository;

@Service
public class ClinicAdminService  {
	
	@Autowired
	private ClinicAdminRepository admins;
	
	public ClinicAdministrator findOne(long id) {
		return admins.findById(id).orElse(null);
	}
	
	
	public ClinicAdministrator save(ClinicAdministrator diagnosis) {
		return admins.save(diagnosis);
	}
	
	public void remove(long id ) {
		admins.deleteById(id);
	}

}
