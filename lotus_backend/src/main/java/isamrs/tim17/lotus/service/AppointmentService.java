package isamrs.tim17.lotus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.dto.RatingDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.DoctorReview;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.repository.AppointmentRepository;

@Service
@Transactional(readOnly = true)
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointments;
	
	@Autowired
	private DoctorReviewService docReviewService;

	@Autowired
	private ClinicReviewService clinicReviewService;
	
	public Appointment findOne(long id) {
		return appointments.findOneById(id);
	}

	public List<Appointment> findByStatus(AppointmentStatus status) {

		return appointments.findByStatus(status);
	}

	public List<Appointment> findAll() {
		return appointments.findAll();
	}

	@Transactional(readOnly = false)
	public Appointment save(Appointment app) {
		return appointments.save(app);
	}
	
	@Transactional(readOnly = false)
	public Appointment saveRatings(Appointment app, RatingDTO rating) {
		Doctor d = app.getDoctor();
		DoctorReview docReview = new DoctorReview(rating.getDoctorRating(), d);
		docReviewService.save(docReview);
		Clinic c = d.getClinic();
		ClinicReview clinicReview = new ClinicReview(rating.getClinicRating(), c);
		clinicReviewService.save(clinicReview);
		app.setReviewed(true);
		return appointments.save(app);
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		appointments.deleteById(id);
	}

	public List<Appointment> findByMedicalRecord(MedicalRecord record) {
		return appointments.findByMedicalRecord(record);
	}
	
	public Page<Appointment> findByMedicalRecord(MedicalRecord record, AppointmentStatus status, Pageable p) {
		return appointments.findAllByMedicalRecordAndStatus(record, status, p);
	}
	

	public List<Appointment> findByDoctor(Doctor record) {
		return appointments.findByDoctor(record);
	}

	public List<Appointment> findByDate(Doctor doctor, Date start, Date end) {
		return appointments.getAllBetweenDates(doctor, start, end);
	}

	public List<Appointment> findFinished(MedicalRecord id) {
		return appointments.getFinishedApps(id);
	}

	public List<Appointment> findByRoomAndDate(Room room, Date startDate, Date endDate) {
		return appointments.getAppointmentsByRoomAndDate(room, startDate, endDate);
	}

	@Transactional(readOnly = false)
	public Appointment schedule(long id, MedicalRecord medicalRecord) throws InterruptedException {
		Appointment a = appointments.findOneById(id);
		if (a == null)
			return a;
		if (!a.getStatus().equals(AppointmentStatus.PREMADE))
			return null;
		a.setStatus(AppointmentStatus.SCHEDULED);
		a.setMedicalRecord(medicalRecord);
		a = appointments.save(a);
		return a;
	}
	
	public List<Appointment> findByClinicAndStatusAndReviewed(Clinic clinic, AppointmentStatus status, Boolean reviewed) {
		return appointments.findByClinicAndStatusAndReviewed(clinic, status, reviewed);
	}

	public Appointment findByKey(String key) {
		return appointments.findByRegKey(key);
	}

}
