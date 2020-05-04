package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.repository.AppointmentTypeRepository;

@Service
public class AppointmentTypeService {
	
	@Autowired
	private AppointmentTypeRepository appointmentTypes;
	
	public AppointmentType findOne(long id) {
		return appointmentTypes.findById(id).orElseGet(null);
	}

	public List<AppointmentType> findAll() {
		return appointmentTypes.findAll();
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
	
	

}
