package isamrs.tim17.lotus.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.RoomRequestType;
import isamrs.tim17.lotus.util.DateUtil;
import isamrs.tim17.lotus.util.RandomUtil;

@Component
public class Scheduler {

	@Autowired
	public RequestService requestService;

	@Autowired
	public RoomService roomService;

	@Autowired
	public CalendarEntryService calService;
	
	@Autowired 
	PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	public MailSenderService mailSender;

	/*
	 * @Scheduled(fixedRate = 1000) public void test() { SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	 * 
	 * Date now = new Date(); String strDate = sdf.format(now);
	 * System.out.println("Fixed Rate scheduler:: " + strDate); }
	 */

	// metoda za automatsko dodeljivanje sale
	@Scheduled(cron = "59 59 23 * * *")
	public void scheduleAppointments() {
		// getuj sve requestove za taj dan koji nisu approved
		// patient app i doctor app
		HashMap<String, Date> period = getPeriod();
		Date startDate = period.get("start");
		Date endDate = period.get("end");
		Date tomorrow = period.get("tomorrow");
		List<RoomRequest> requests = requestService.findByDateRangeApps(startDate, endDate);

		// za svaki uradi zakazivanje i posalji mejl
		for (RoomRequest rr : requests) {
			// get sve sobe iz klinike
			List<Doctor> doctors = new ArrayList<>(rr.getDoctors());
			Doctor doc = doctors.get(0);
			Patient pat = patientService.findOne(rr.getPatient());
			Clinic c = doc.getClinic();
			List<Room> rooms = roomService.findByClinic(c);
			for (Room r : rooms) {
				Date term = findFirstTerm(r, tomorrow);
				if (term != null) {
					//zakazi
					Appointment app = new Appointment();
					if (rr.getType().equals(RoomRequestType.PATIENT_APP)) {
						app.setRegKey(RandomUtil.buildAuthString());
						app.setStatus(AppointmentStatus.UNCONFIRMED);
					}
					else
						app.setStatus(AppointmentStatus.SCHEDULED);

					app.setRoom(r);
					app.setClinic(c);
					app.setDoctor(doc);
					app.setMedicalRecord(pat.getMedicalRecord());
					app.setStartDate(term);
					app.setEndDate(DateUtil.addMinutes(term, 30));
					app.setAppointmentType(doc.getSpecialty().getType());
					app.setPrice(rr.getPrice()); // sacuvana cena iz requesta
					app.setDiscount(0);

					CalendarEntry entry = new CalendarEntry(app);
					rr.setStatus(RequestStatus.APPROVED);
					
					try {
						appointmentService.save(app, rr, entry); // pokusavamo sve da sacuvamo
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (rr.getType().equals(RoomRequestType.PATIENT_APP))
						mailSender.sendPatientRequest(app);
					else
						mailSender.sendPatientNotification(app);
					mailSender.sendDoctorNotification(app);


				}
			}
		}
	}

	private HashMap<String, Date> getPeriod() {
		HashMap<String, Date> period = new HashMap<>();
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		today = DateUtil.cleanDate(today);
		period.put("start", today);

		period.put("end", DateUtil.endOfDay(today));

		cal.setTime(DateUtil.cleanDate(today));
		cal.add(Calendar.DATE, 1);
		period.put("tomorrow", cal.getTime());
		return period;

	}

	private Date findFirstTerm(Room room, Date startDate) {
		List<Date> allDays = DateUtil.getSevenDays(startDate);
		for (Date d : allDays) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.set(Calendar.HOUR_OF_DAY, 7);
			Date currentDate = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 18);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date endDate = cal.getTime();
			List<CalendarEntry> entries = calService.findByRoomAndDate(room, currentDate, endDate);
			// List<Appointment> apps = appService.findByRoomAndDate(r, d, endDate);
			// svi termini u jednom danu, pocevsi sa satom prosledjenog datuma
			List<Date> allTermsInDay = DateUtil.getAllTerms(d, true);
			// pronadji prvi slobodan termin za tu sobu tog dana
			List<Date> clearTerms = DateUtil.removeOverlap(allTermsInDay, entries);
			Date firstTerm = !clearTerms.isEmpty() ? clearTerms.get(0) : null;
			if (firstTerm != null) {
				return firstTerm;
			}
		}
		return null;
	}

}
