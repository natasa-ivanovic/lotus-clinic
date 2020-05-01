package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.repository.AppointmentRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointments;
	
	public Appointment findOne(long id) {
		return appointments.findById(id).orElseGet(null);
	}
	
	public List<Appointment> findByStatus(AppointmentStatus status) {
		
		return appointments.findByStatus(status);
	}

	public List<Appointment> findAll() {
		return appointments.findAll();
	}
	
	public Appointment save(Appointment  app) {
		return appointments.save(app);
	}

	public void remove(long id) {
		appointments.deleteById(id);
	}
	

}
