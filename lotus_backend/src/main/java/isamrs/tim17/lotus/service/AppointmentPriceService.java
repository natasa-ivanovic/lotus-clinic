package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.repository.AppointmentPriceRepository;

@Service
public class AppointmentPriceService {
	
	@Autowired
	private AppointmentPriceRepository appointmentPrices;
	
	public List<AppointmentPrice> findAll() {
		return appointmentPrices.findAll();
	}
	
	public AppointmentPrice save(AppointmentPrice ap) {
		return appointmentPrices.save(ap);
	}
	
	public AppointmentPrice findOne(long id) {
		return appointmentPrices.findById(id).orElseGet(null);
	}
}
