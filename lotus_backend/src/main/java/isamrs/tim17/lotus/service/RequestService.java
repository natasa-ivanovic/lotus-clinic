package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.repository.RequestRepository;

@Service
public class RequestService {
	@Autowired
	private RequestRepository requests;
	
	public Request save(Request request) {
		return requests.save(request);
	}
	
	public List<RegistrationRequest> findRegistrations() {
		return requests.getAllRegistrations();
	}
	
	public Request findOne(long id) {
		return requests.findById(id).orElseGet(null);
	}
	
	public Request findOneByKey(String key) {
		List<RegistrationRequest> li = requests.findByKey(key);
		return li.get(0);
	}

	public List<Request> findAll() {
		return requests.findAll();
	}
	
	public void remove(long id) {
		requests.deleteById(id);
	}

}