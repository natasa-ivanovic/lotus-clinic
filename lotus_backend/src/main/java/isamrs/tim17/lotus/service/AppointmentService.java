package isamrs.tim17.lotus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MedicalRecord;
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

	public Appointment save(Appointment app) {
		return appointments.save(app);
	}

	public void remove(long id) {
		appointments.deleteById(id);
	}

	public List<Appointment> findByMedicalRecord(MedicalRecord record) {
		return appointments.findByMedicalRecord(record);
	}

	public List<Appointment> findByDoctor(Doctor record) {
		return appointments.findByDoctor(record);
	}

	
	 public List<Appointment> findByDate(Doctor doctor, Date start, Date end) {
	 System.out.println(start); System.out.println(end); 
	 return appointments.getAllBetweenDates(doctor, start, end); 
	 }
	 

		/*
		 * public List<Appointment> findByDate(Doctor doctor) { return
		 * appointments.findAllByDoctor(doctor); }
		 */

}
