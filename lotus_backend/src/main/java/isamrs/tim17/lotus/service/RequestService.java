package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired
	private RequestRepository requests;
	
	public Request findOne(long id) {
		return requests.findById(id).orElseGet(null);
	}

	public List<Request> findAll() {
		return requests.findAll();
	}
	
	public Request save(Request req) {
		return requests.save(req);
	}

	public void remove(long id) {
		requests.deleteById(id);
	}

}
