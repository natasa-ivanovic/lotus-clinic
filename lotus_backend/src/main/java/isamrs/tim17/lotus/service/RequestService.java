package isamrs.tim17.lotus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.VacationRequest;
import isamrs.tim17.lotus.repository.RequestRepository;

@Service
@Transactional(readOnly = true)
public class RequestService {
	@Autowired
	private RequestRepository requests;

	@Autowired
	private DoctorService doctors;
	
	@Transactional(readOnly = false)
	public Request save(Request request, Long doctor) {
		Doctor d = doctors.findOne(doctor);
		// forces the version increment to update
		d.setLastRequested(new Date());
		Request r = requests.save(request);
		doctors.save(d);
		return r;
	}
	
	@Transactional(readOnly = false)
	public Request save(Request request) {
		return requests.save(request);
	}
	
	public List<RegistrationRequest> findRegistrations() {
		return requests.getAllRegistrations();
	}
	
	public Request findOne(long id) {
		return requests.findOneById(id);
	}
	
	public Request findOneByKey(String key) {
		List<RegistrationRequest> li = requests.findByKey(key);
		return li.get(0);
	}

	public List<Request> findAll() {
		return requests.findAll();
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		requests.deleteById(id);
	}
	
	public List<RoomRequest> getRoomRequests() {
		return requests.getAllRoomRequests();
	}

	public RoomRequest findByStartDateAndDoctor(Date startDate, long doctor) {
		return requests.findOneByDateAndDoctor(startDate, doctor);
	}
	
	
	public List<VacationRequest> getVacationRequests(Clinic clinic, RequestStatus status)
	{
		return requests.findByClinicAndStatus(clinic, status);
	}
}
