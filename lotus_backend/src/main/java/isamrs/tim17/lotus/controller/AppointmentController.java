package isamrs.tim17.lotus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.AppointmentDTO;
import isamrs.tim17.lotus.dto.ClinicDTO;
import isamrs.tim17.lotus.dto.DiagnosisDTO;
import isamrs.tim17.lotus.dto.DoctorDTO;
import isamrs.tim17.lotus.dto.MedicineDTO;
import isamrs.tim17.lotus.dto.MedicineDiagnosisDTO;
import isamrs.tim17.lotus.dto.PatientRequest;
import isamrs.tim17.lotus.dto.PremadeAppDTO;
import isamrs.tim17.lotus.dto.RoomAndRequestDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.AppointmentType;
import isamrs.tim17.lotus.model.CalendarEntry;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.model.Diagnosis;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.DoctorReview;
import isamrs.tim17.lotus.model.MedicalRecord;
import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.model.Nurse;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.Prescription;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.model.RoomRequestType;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.ClinicReviewService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.DiagnosisService;
import isamrs.tim17.lotus.service.DoctorReviewService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.MailSenderService;
import isamrs.tim17.lotus.service.MedicineService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.util.DateUtil;
import isamrs.tim17.lotus.util.RandomUtil;
import isamrs.tim17.lotus.util.RatingUtil;

@RestController
@RequestMapping("/api")
public class AppointmentController {

	@Autowired
	private AppointmentService service;
	@Autowired
	private RoomService roomService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private RequestService requestService;
	@Autowired
	public MailSenderService mailSender;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private DiagnosisService diagnosisService;
	@Autowired
	private CalendarEntryService calendarService;
	@Autowired
	private AppointmentTypeService typeService;
	@Autowired
	private ClinicReviewService clinicReviewService;
	@Autowired
	private DoctorReviewService doctorReviewService;

	@GetMapping("/appointments")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<List<Appointment>> getAppointments() {

		List<Appointment> appointments = service.findAll();
		return new ResponseEntity<>(appointments, HttpStatus.OK);

	}

	@GetMapping("/appointments/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<Appointment> getAppointment(@PathVariable("id") long id) {
		Appointment app = service.findOne(id);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(app, HttpStatus.OK);
	}

	/**
	 * This method is used for getting a list of all premade appointments.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity This returns the list of premade appointments and the
	 *         status code.
	 */
	@GetMapping("/appointments/premade")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PremadeAppDTO>> getPremadeApps() {
		List<Appointment> app = service.findByStatus(AppointmentStatus.PREMADE);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		List<PremadeAppDTO> dto = new ArrayList<>();
		for (Appointment a : app) {
			dto.add(new PremadeAppDTO(a));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * This method is used for scheduling a premade appointment.
	 * 
	 * @param id Long which is the ID of the premade appointment.
	 * @return ResponseEntity Status code.
	 */
	@PostMapping("/appointments/schedule/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> scheduleApp(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		Appointment app = null;
		try {
			app = service.schedule(id, patient.getMedicalRecord());
		} catch (Exception e) {
			return new ResponseEntity<>("The appointment you tried to schedule is already scheduled.",
					HttpStatus.BAD_REQUEST);
		}
		if (app == null)
			return new ResponseEntity<>("The appointment you tried to schedule is either already scheduled or doesn't exit.", HttpStatus.BAD_REQUEST);


		mailSender.sendPatientPremadeScheduled(app);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * This method is used so patients can get their appointments for displaying on
	 * the home page.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity Status code with the list of appointment dto's.
	 */
	@GetMapping("/appointments/patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PremadeAppDTO>> getMyAppointments() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();

		List<Appointment> apps = service.findByMedicalRecord(patient.getMedicalRecord());
		if (apps == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		List<PremadeAppDTO> dto = new ArrayList<>();
		for (Appointment app : apps) {
			if (app.getStatus().equals(AppointmentStatus.SCHEDULED))
				dto.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * This method is used so patients can get their appointments for displaying in
	 * a table.
	 * 
	 * @param pageNo     integer which represents the page number for the pageable
	 *                   object.
	 * @param pageSize   integer which represents the page size for the pageable
	 *                   object.
	 * @param sortBy     string which represents the sort criteria for the pageable
	 *                   object.
	 * @param descending string which represents the sorting direction for the
	 *                   pageable object.
	 * @return ResponseEntity Status code with the list of appointment dto's.
	 */
	@GetMapping("/appointments/patient/past")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> getMyPastAppointments(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();

		return getPastAppointments(patient.getMedicalRecord(), pageNo, pageSize, sortBy, descending);
	}

	/**
	 * This method is used so patients can get their appointments for displaying in
	 * a table or so that doctors can get their patients appointments.
	 * 
	 * @param id         integer which is the id of the queried patient.
	 * @param pageNo     integer which represents the page number for the pageable
	 *                   object.
	 * @param pageSize   integer which represents the page size for the pageable
	 *                   object.
	 * @param sortBy     string which represents the sort criteria for the pageable
	 *                   object.
	 * @param descending string which represents the sorting direction for the
	 *                   pageable object.
	 * @return ResponseEntity Status code with the list of appointment dto's.
	 */
	@GetMapping("/appointments/patient/{id}/past")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<Object> getPatientsPastAppointments(@PathVariable("id") String id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") String descending) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		long patientId;
		try {
			patientId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Forwarded id isn't a number", HttpStatus.BAD_REQUEST);
		}
		if (user.getRole().equals("PATIENT") && user.getId() != patientId)
			return new ResponseEntity<>("Cannot get another patient's medical history!", HttpStatus.BAD_REQUEST);
		Patient patient = patientService.findOne(patientId);
		if (patient == null)
			return new ResponseEntity<>("Patient with specified ID doesn't exist in database!", HttpStatus.BAD_REQUEST);
		return getPastAppointments(patient.getMedicalRecord(), pageNo, pageSize, sortBy, descending);
	}

	/**
	 * This method is used to handle getting all the data in a specific page from
	 * the database.
	 * 
	 * @param medicalRecord MedicalRecord object of the selected patient.
	 * @param pageNo        integer which represents the page number for the
	 *                      pageable object.
	 * @param pageSize      integer which represents the page size for the pageable
	 *                      object.
	 * @param sortBy        string which represents the sort criteria for the
	 *                      pageable object.
	 * @param descending    string which represents the sorting direction for the
	 *                      pageable object.
	 * @return ResponseEntity Status code with the list of appointment dto's.
	 */
	public ResponseEntity<Object> getPastAppointments(MedicalRecord medicalRecord, Integer pageNo, Integer pageSize,
			String sortBy, String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<Appointment> apps = null;
		try {
			apps = service.findByMedicalRecord(medicalRecord, AppointmentStatus.FINISHED, paging);

		} catch (Exception e) {
			return new ResponseEntity<>("Error in forwarded arguments for sort!", HttpStatus.BAD_REQUEST);
		}
		if (apps == null)
			return new ResponseEntity<>("Something went wrong. Please try again later.", HttpStatus.BAD_REQUEST);
		Page<PremadeAppDTO> dto = apps.map(new Function<Appointment, PremadeAppDTO>() {
			@Override
			public PremadeAppDTO apply(Appointment app) {
				return new PremadeAppDTO(app);
			}
		});
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * This method is used to return a list of free terms for doctors which can do a
	 * specific type of appointment. It returns either a list of doctors and their
	 * free terms or a list of clinics which a list of doctors and their free terms.
	 * 
	 * @param pr Patient request object which contains the requestDate,
	 *           appointmentType and a boolean which indicates whether the user
	 *           wants to see clinics or just their doctors.
	 * @return ResponseEntity This returns the HTTP status code along the objects
	 *         that were requested.
	 */
	@PostMapping("/appointments/request")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> requestList(@RequestBody PatientRequest pr) {
		if (pr.getRequestDate() == 0)
			return new ResponseEntity<>("No date specified!", HttpStatus.BAD_REQUEST);
		Date date = new Date(pr.getRequestDate());
		AppointmentType type = typeService.findOne(pr.getAppointmentType());
		if (type == null)
			return new ResponseEntity<>("No appointment type specified!", HttpStatus.BAD_REQUEST);
		if (pr.isClinics()) {
			List<Clinic> clinics = clinicService.findAll();
			List<ClinicDTO> clinicList = new ArrayList<>();
			for (Clinic c : clinics) {
				if (c.getDoctors().isEmpty())
					continue;
				List<DoctorDTO> doctors = getDoctorTerms(c.getDoctors(), type, date);
				if (!doctors.isEmpty()) {
					ClinicDTO dto = new ClinicDTO(c);
					List<ClinicReview> reviews = clinicReviewService.findAllByClinic(c);
					double rating = RatingUtil.getAverageClinicRating(reviews);
					dto.setRating(rating);
					dto.setDoctors(doctors);
					dto.setPrice(doctors.get(0).getPrice());
					clinicList.add(dto);
				}
			}
			if (clinicList.isEmpty())
				return new ResponseEntity<>("No clinics fulfil the criteria requested!", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(clinicList, HttpStatus.OK);
		} else {
			List<Doctor> doctors = doctorService.findAll();
			List<DoctorDTO> results = getDoctorTerms(doctors, type, date);
			if (results.isEmpty())
				return new ResponseEntity<>("No doctors fulfil  the criteria requested!", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(results, HttpStatus.OK);
		}
	}

	/**
	 * This method is used to get free terms for all doctors in forwarded collection
	 * for a specific day and type.
	 * 
	 * @param doctors   Collection<Doctor> which is iterated through and checked for
	 *                  equality with requested appointment type
	 * @param type      AppointmentType which was requested by the patient
	 * @param startDate Date object which equates to the requested date
	 * @return List of all valid DoctorDTO objects which contain their free terms
	 *         for the requested day
	 */
	private List<DoctorDTO> getDoctorTerms(Collection<Doctor> doctors, AppointmentType type, Date startDate) {
		Iterator<Doctor> docIt = doctors.iterator();
		List<DoctorDTO> results = new ArrayList<>();
		while (docIt.hasNext()) {
			Doctor d = docIt.next();
			if (d.getSpecialty().getType().getId() != type.getId()) {
				continue;
			}
			List<Date> availableDates = DateUtil.getAllTerms(startDate, false);
			Date endDate = DateUtil.endOfDay(startDate);
			List<CalendarEntry> calendarEntries = calendarService.findByMedicalPersonAndDate(d, startDate, endDate);
			availableDates = DateUtil.removeOverlap(availableDates, calendarEntries);
			List<RoomRequest> requestList = requestService.findByDateRangeAndDoctor(d, startDate, endDate);
			availableDates = DateUtil.removeOverlapRequests(availableDates, requestList);
			if (!availableDates.isEmpty()) {
				List<DoctorReview> ratingList = doctorReviewService.findAllByDoctor(d);
				double rating = RatingUtil.getAverageDoctorRating(ratingList);
				results.add(new DoctorDTO(d, rating, availableDates));
			}
		}
		return results;
	}

	/**
	 * This method is used so patients can cancel their scheduled appointments.
	 * 
	 * @param id integer which is the id of scheduled appointment
	 * @return ResponseEntity Status code with the optional error message.
	 */
	@GetMapping("/appointments/patient/cancel/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> cancelAppointmentPatient(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();

		Appointment app = service.findOne(id);
		if (app == null)
			return new ResponseEntity<>("Appointment not found", HttpStatus.BAD_REQUEST);
		if (app.getMedicalRecord().getId() != patient.getId())
			return new ResponseEntity<>("Cannot cancel another patient's appointment!", HttpStatus.BAD_REQUEST);
		Date now = new Date();
		Date appDate = app.getStartDate();

		if (appDate.getTime() - now.getTime() < 86400000)
			return new ResponseEntity<>("Cannot cancel appointment which starts in less than 24 hours!",
					HttpStatus.BAD_REQUEST);
		
		// provera da li moze da otkaze
		if (!app.getStatus().equals(AppointmentStatus.SCHEDULED))
			return new ResponseEntity<>("Cannot cancel appointment that isn't scheduled!", HttpStatus.BAD_REQUEST);

		// transakcija
		try {
			calendarService.cancelAppointment(app.getId());
		} catch (Exception e) {
			return new ResponseEntity<>("Appointment has already been canceled!", HttpStatus.BAD_REQUEST);
		}

		mailSender.sendDoctorCanceledAppointment(app);

		return new ResponseEntity<>(HttpStatus.OK);
	}


	@GetMapping("/appointments/doctor/cancel/{id}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> cancelAppointmentDoctor(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();

		CalendarEntry ce = calendarService.findOne(id);

		if (ce == null)
			return new ResponseEntity<>("Appointment not found", HttpStatus.BAD_REQUEST);

		Appointment app = ce.getAppointment();
		if (app == null)
			return new ResponseEntity<>("Appointment not found", HttpStatus.BAD_REQUEST);

		// ne moze da otkazuje preglede drugih lekara
		if (app.getDoctor().getId() != doctor.getId())
			return new ResponseEntity<>(
					"Something went wrong while canceling the appointment. Cannot cancel the appointment.",
					HttpStatus.BAD_REQUEST);

		Date now = new Date();
		Date appDate = app.getStartDate();

		if (appDate.getTime() - now.getTime() < 86400000)
			return new ResponseEntity<>("Cannot cancel appointment which starts in less than 24 hours!",
					HttpStatus.BAD_REQUEST);

		// provera da li moze da otkaze
		if (!app.getStatus().equals(AppointmentStatus.SCHEDULED))
			return new ResponseEntity<>("Cannot cancel appointment that isn't scheduled!", HttpStatus.BAD_REQUEST);

		// transakcija
		try {
			calendarService.cancelAppointment(app.getId());
		} catch (Exception e) {
			return new ResponseEntity<>("Appointment has already been canceled!", HttpStatus.BAD_REQUEST);
		}
		
		mailSender.sendPatientCanceledAppointment(app);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * This method is used so doctors can get their appointments.
	 * 
	 * @param Nothing.
	 * @return ResponseEntity Status code with the list of appointment dto's.
	 */
	@GetMapping("/appointments/doctor")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getDoctorAppointments() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();

		List<Appointment> apps = service.findByDoctor(doctor);
		if (apps == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		List<PremadeAppDTO> dto = new ArrayList<>();
		for (Appointment app : apps) {

			// TODO check if premade appointments should be here
			if (app.getStatus() == AppointmentStatus.PREMADE || app.getStatus() == AppointmentStatus.CANCELED)
				continue;
			PremadeAppDTO newDTO = new PremadeAppDTO(app);
			dto.add(newDTO);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping("/appointments/doctor/today")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getTodaysAppointment(@RequestBody String startDate) {
		// TODO mozda promeniti ovo da bude long ipak
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();

		long sd = Long.parseLong(startDate);
		HashMap<String, Date> period = getPeriod(sd);
		Date start = period.get("start");
		Date end = period.get("end");

		List<Appointment> apps = service.findByDate(doctor, start, end);
		List<PremadeAppDTO> info = new ArrayList<>();
		for (Appointment app : apps) {
			if (app.getStatus() == AppointmentStatus.SCHEDULED) {
				PremadeAppDTO dto = new PremadeAppDTO(app);
				info.add(dto);

			}
		}
		return new ResponseEntity<>(info, HttpStatus.OK);
	}

	@GetMapping("appointments/finished")
	@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getFinishedAppointments(@RequestBody String patientId) {

		if (patientId == null || "".equals(patientId))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		long id = Long.parseLong(patientId);
		Patient patient = patientService.findOne(id);

		List<Appointment> apps = service.findFinished(patient.getMedicalRecord());
		List<PremadeAppDTO> finished = new ArrayList<>();
		for (Appointment app : apps) {
			PremadeAppDTO dto = new PremadeAppDTO(app);
			finished.add(dto);
		}

		return new ResponseEntity<>(finished, HttpStatus.OK);
	}

	@GetMapping("appointments/nurse")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<List<PremadeAppDTO>> getAppointmentsForNurse() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Nurse nurse = (Nurse) a.getPrincipal();

		List<Appointment> apps = service.findByClinicAndStatusAndReceptApproved(nurse.getClinic(), AppointmentStatus.FINISHED,
				false);
		List<PremadeAppDTO> appsDtos = new ArrayList<>();
		for (Appointment app : apps) {
			appsDtos.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(appsDtos, HttpStatus.OK);
	}

	@PostMapping("appointments/nurse")
	@PreAuthorize("hasRole('NURSE')")
	public ResponseEntity<Object> setAppointmentReview(@RequestBody String idstr) {
		long id;
		try {
			id = Long.parseLong(idstr);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Appointment appointment = service.findOne(id);
		appointment.setReceptApproved(true);
		service.save(appointment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private HashMap<String, Date> getPeriod(long startDate) {
		Date date = new Date(startDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date start = cal.getTime();

		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date end = cal.getTime();

		HashMap<String, Date> period = new HashMap<>();
		period.put("start", start);
		period.put("end", end);

		return period;
	}
	
	@PostMapping("/appointments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentDTO app) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdministrator admin = (ClinicAdministrator) a.getPrincipal();

		SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = null;
		Date end = new Date(app.getEndDateLong());
		try {
			start = sdfStart.parse(app.getStartDateString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Room room = roomService.findOne(app.getRoom());

		Doctor doc = doctorService.findOne(app.getDoctor());

		AppointmentPrice at = doc.getSpecialty();

		Clinic clinic = clinicService.findOne(admin.getClinic().getId());

		Appointment newApp = new Appointment(start, end, at.getPrice(), app.getDiscount(), at.getType(), doc, room,
				clinic);
		service.save(newApp);
		CalendarEntry entry = new CalendarEntry(newApp);
		calendarService.save(entry);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/appointments/finish")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<Object> finishAppointment(@RequestBody PremadeAppDTO app) {
		if (app == null || app.getId() <= 0 || app.getDiagnosis() == null || app.getDiagnosis().isEmpty()
				|| app.getDescription() == null
				|| "".equals(app.getDescription())) {
			return new ResponseEntity<>("Fill in all required fields!", HttpStatus.BAD_REQUEST);
		}

		Appointment appointment = service.findOne(app.getId());
		// setovati listu bolesti
		Set<Diagnosis> diagnosis = new HashSet<>();
		for (String d : app.getDiagnosis()) {
			long id = 0;
			try {
				id = Long.parseLong(d);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Diagnosis dg = diagnosisService.findOne(id);
			dg.getAppointments().add(appointment);
			diagnosis.add(dg);
		}
		Set<Prescription> prescriptions = new HashSet<>();
		for (String r : app.getRecipes()) {
			long id = 0;
			try {
				id = Long.parseLong(r);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Medicine med = medicineService.findOne(id);
			Prescription presp = new Prescription();
			presp.setMedicine(med);
			presp.setAppointment(appointment);
			prescriptions.add(presp);
		}

		appointment.setDiagnosis(diagnosis);
		appointment.setPrescriptions(prescriptions);
		appointment.setInformation(app.getDescription());
		appointment.setStatus(AppointmentStatus.FINISHED);

		service.save(appointment);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping("/appointments/notification")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> sendNotification(@RequestBody RoomAndRequestDTO dto) {
		Date today = new Date();
		if (dto.getRequest() == 0 || dto.getRoom() == 0 || dto.getStartDate() == 0
				|| dto.getStartDate() < (today.getTime()))
			return new ResponseEntity<>("All fields should be filled!", HttpStatus.BAD_REQUEST);

		long roomId = dto.getRoom();
		long requestId = dto.getRequest();
		Date startDate = new Date(dto.getStartDate());
		Date endDate = DateUtil.addMinutes(startDate, 30);
		Room room = roomService.findOne(roomId);
		RoomRequest rr = (RoomRequest) requestService.findOne(requestId);
		if (room == null || rr == null || !rr.getStatus().equals(RequestStatus.PENDING))
			return new ResponseEntity<>("Request already approved or declined!", HttpStatus.BAD_REQUEST);
		if (rr.getType().equals(RoomRequestType.DOCTOR_OPER))
			return new ResponseEntity<>("Allowed only for appointment requests!", HttpStatus.BAD_REQUEST);

		List<Doctor> doctors = getDoctors(rr);
		Doctor doctor = doctors.get(0);
		Patient patient = patientService.findOne(rr.getPatient());
		Clinic clinic = doctor.getClinic();

		Appointment app = new Appointment();
		if (rr.getType().equals(RoomRequestType.PATIENT_APP)) {
			app.setRegKey(RandomUtil.buildAuthString());
			app.setStatus(AppointmentStatus.UNCONFIRMED);
		}
		else
			app.setStatus(AppointmentStatus.SCHEDULED);

		app.setRoom(room);
		app.setClinic(clinic);
		app.setDoctor(doctor);
		app.setMedicalRecord(patient.getMedicalRecord());
		app.setStartDate(startDate);
		app.setEndDate(endDate);
		app.setAppointmentType(doctor.getSpecialty().getType());
		app.setPrice(rr.getPrice()); // sacuvana cena iz requesta
		app.setDiscount(0);

		CalendarEntry entry = new CalendarEntry(app);
		rr.setStatus(RequestStatus.APPROVED);

		try {
			service.save(app, rr, entry); // pokusavamo sve da sacuvamo
		} catch (Exception e) {
			return new ResponseEntity<>("Room already scheduled for selected term!", HttpStatus.BAD_REQUEST);
		}

		if (rr.getType().equals(RoomRequestType.PATIENT_APP))
			mailSender.sendPatientRequest(app);
		else
			mailSender.sendPatientNotification(app);
		mailSender.sendDoctorNotification(app);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/appointments/info")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<MedicineDiagnosisDTO> getDataForAppointment() {

		List<MedicineDTO> medicinesDTO = new ArrayList<>();
		List<DiagnosisDTO> diagnosisDTO = new ArrayList<>();

		List<Medicine> medicines = medicineService.findAll();
		List<Diagnosis> diagnosis = diagnosisService.findAll();

		for (Medicine m : medicines) {
			MedicineDTO med = new MedicineDTO(m);
			medicinesDTO.add(med);
		}

		for (Diagnosis d : diagnosis) {
			DiagnosisDTO illness = new DiagnosisDTO(d);
			diagnosisDTO.add(illness);
		}

		MedicineDiagnosisDTO dto = new MedicineDiagnosisDTO(medicinesDTO, diagnosisDTO);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@GetMapping("/confirm/{key}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> confirmAppointment(@PathVariable("key") String key) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Patient p = (Patient) auth.getPrincipal();
		Appointment a = service.findByKey(key);
		if (a == null)
			return new ResponseEntity<>("Appointment with specified key not found!", HttpStatus.BAD_REQUEST);
		if (a.getMedicalRecord().getPatient().getId() != p.getId())
			return new ResponseEntity<>("Cannot confirm another patient's appointment!", HttpStatus.BAD_REQUEST);
		a.setRegKey(null);
		a.setStatus(AppointmentStatus.SCHEDULED);
		service.save(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private List<Doctor> getDoctors(RoomRequest r) {
		Set<Doctor> docs = r.getDoctors();
		List<Doctor> doctors = new ArrayList<>();

		Iterator<Doctor> it = docs.iterator();
		while (it.hasNext()) {
			Doctor d = doctorService.findOne(it.next().getId());
			doctors.add(d);
		}

		return doctors;
	}
	
	@GetMapping("/appointments/doctor/patient/{id}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getAppointmentDoctorPatient(@PathVariable("id") long id){
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		
		Patient patient = patientService.findOne(id);
		List<Appointment> appointments = service.findByDoctorAndStatusAndMedicalRecord(doctor, AppointmentStatus.SCHEDULED, patient.getMedicalRecord());
		List<PremadeAppDTO> appointmentDTOs = new ArrayList<>();
		for(Appointment app : appointments) {
			appointmentDTOs.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/appointments/doctor/patient/{id}/past")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getPastAppointments(@PathVariable("id") long id) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		Patient patient = patientService.findOne(id);
		
		List<Appointment> appointments = service.findByDoctorAndStatusAndMedicalRecord(doctor, AppointmentStatus.FINISHED, patient.getMedicalRecord());
		List<PremadeAppDTO> appointmentDTOs = new ArrayList<>();
		for(Appointment app : appointments) {
			appointmentDTOs.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
	}

}
