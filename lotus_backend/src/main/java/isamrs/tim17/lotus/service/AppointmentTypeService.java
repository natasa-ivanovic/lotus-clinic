package isamrs.tim17.lotus.service;


import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.repository.AppointmentTypeRepository;

@Service
public class AppointmentTypeService {
	
	@Autowired
	private AppointmentTypeRepository appointmentTypes;
	
	public AppointmentType findOne(long id) {
		return appointmentTypes.findOneById(id);
	}

	public List<AppointmentType> findAll() {
		return appointmentTypes.findAll();
	}
	
	public List<AppointmentType> findAllAppointments() {
		return appointmentTypes.findAllByOperation(false);
	}
	
	public AppointmentType save(AppointmentType  at) {
		return appointmentTypes.save(at);
	}

	public void remove(long id) {
		appointmentTypes.deleteById(id);
	}
	
	public AppointmentType findOne(String name) {
		return appointmentTypes.findOneByNameIgnoringCase(name);
	}

	public Page<AppointmentType> findAllPage(Pageable paging) {
		return appointmentTypes.findAll(paging);
	}
	
	

}
