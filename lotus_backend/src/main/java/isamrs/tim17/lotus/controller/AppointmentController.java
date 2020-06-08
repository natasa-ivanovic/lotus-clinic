package isamrs.tim17.lotus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.AppointmentDTO;
import isamrs.tim17.lotus.dto.DiagnosisDTO;
import isamrs.tim17.lotus.dto.MedicineDTO;
import isamrs.tim17.lotus.dto.MedicineDiagnosisDTO;
import isamrs.tim17.lotus.dto.PremadeAppDTO;
import isamrs.tim17.lotus.dto.RoomAndRequestDTO;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentPrice;
import isamrs.tim17.lotus.model.AppointmentStatus;
import isamrs.tim17.lotus.model.Clinic;
import isamrs.tim17.lotus.model.ClinicAdministrator;
import isamrs.tim17.lotus.model.Diagnosis;
import isamrs.tim17.lotus.model.Doctor;
import isamrs.tim17.lotus.model.MailSenderModel;
import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RequestStatus;
import isamrs.tim17.lotus.model.Room;
import isamrs.tim17.lotus.model.RoomRequest;
import isamrs.tim17.lotus.service.AppointmentService;
import isamrs.tim17.lotus.service.AppointmentTypeService;
import isamrs.tim17.lotus.service.CalendarEntryService;
import isamrs.tim17.lotus.service.ClinicService;
import isamrs.tim17.lotus.service.DiagnosisService;
import isamrs.tim17.lotus.service.DoctorService;
import isamrs.tim17.lotus.service.MedicineService;
import isamrs.tim17.lotus.service.PatientService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.RoomService;
import isamrs.tim17.lotus.util.DateUtil;

@RestController
@RequestMapping("/api")
public class AppointmentController {
	
	@Autowired private AppointmentService service;
	@Autowired private RoomService roomService;
	@Autowired private AppointmentTypeService appointmentTypeService;
	@Autowired private DoctorService doctorService;
	@Autowired private ClinicService clinicService;
	@Autowired private PatientService patientService;
	@Autowired private RequestService requestService;
	@Autowired public MailSenderModel mailSender;
	@Autowired private MedicineService medicineService;
	@Autowired private DiagnosisService diagnosisService;
	@Autowired private CalendarEntryService calendarService;
	
	
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
	
	@PostMapping("/appointments/schedule/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Object> scheduleApp(@PathVariable("id") long id) {
		Appointment app = service.findOne(id);
		if (app == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		app.setStatus(AppointmentStatus.SCHEDULED);
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		app.setMedicalRecord(patient.getMedicalRecord());
		service.save(app);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
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
	
	@GetMapping("/appointments/patient/past")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PremadeAppDTO>> getMyPastAppointments() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = (Patient) a.getPrincipal();
		
		List<Appointment> apps = service.findByMedicalRecord(patient.getMedicalRecord());
		if (apps == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		List<PremadeAppDTO> dto = new ArrayList<>();
		for (Appointment app : apps) {
			if (app.getStatus().equals(AppointmentStatus.FINISHED))
				dto.add(new PremadeAppDTO(app));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
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
			if(app.getStatus() == AppointmentStatus.PREMADE || app.getStatus() == AppointmentStatus.CANCELED)
				continue;
			PremadeAppDTO newDTO = new PremadeAppDTO(app);
			dto.add(newDTO);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/appointments/doctor/today") 
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<List<PremadeAppDTO>> getTodaysAppointment(@RequestBody String startDate) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = (Doctor) a.getPrincipal();
		
		long sd = Long.parseLong(startDate);
		HashMap<String, Date> period = getPeriod(sd);
		Date start = period.get("start");
		Date end = period.get("end");
		
		List<Appointment> apps = service.findByDate(doctor, start, end);
		List<PremadeAppDTO> info = new ArrayList<>();
		for (Appointment app: apps) {
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
		for (Appointment app: apps) {
			PremadeAppDTO dto = new PremadeAppDTO(app);
			finished.add(dto);
		}
		
		return new ResponseEntity<>(finished, HttpStatus.OK);
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
		
		Appointment newApp = new Appointment(start, end, at.getPrice(), app.getDiscount(), at.getType(), doc, room, clinic);
		service.save(newApp);
		CalendarEntry entry = new CalendarEntry(newApp);
		calendarService.save(entry);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/appointments/notification")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> sendNotification(@RequestBody RoomAndRequestDTO dto) {
		
		if (dto.getRequest() == 0 || dto.getRoom() == 0 || dto.getStartDate() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		long roomId = dto.getRoom();
		long requestId = dto.getRequest();
		Date startDate = new Date(dto.getStartDate());
		Date endDate = DateUtil.addMinutes(startDate, 30);
		
		//nadji rikvest i sobu, napravi appointment, setuj na zakazan, sacuvaj u bazu
		Room room = roomService.findOne(roomId);
		RoomRequest rr = (RoomRequest) requestService.findOne(requestId);
		if (room == null || rr == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Doctor doctor = doctorService.findOne(rr.getDoctor());
		Patient patient = patientService.findOne(rr.getPatient());
		Clinic clinic = doctor.getClinic();
		AppointmentStatus status = AppointmentStatus.SCHEDULED;
		
		Appointment app = new Appointment();
		
		app.setRoom(room);
		app.setClinic(clinic);
		app.setDoctor(doctor);
		app.setMedicalRecord(patient.getMedicalRecord());
		app.setStatus(status);
		app.setStartDate(startDate);
		app.setEndDate(endDate);
		app.setAppointmentType(doctor.getSpecialty().getType());
		app.setPrice(doctor.getSpecialty().getPrice());
		service.save(app);
		
		CalendarEntry entry = new CalendarEntry(app);
		calendarService.save(entry);
		
		
		String contentPatient = "Hello " + patient.getName() + " " + patient.getSurname() + "!\nIn response to your appointment request, we have created a term for you in our centre.\n"
				+ "The appointment is scheduled for " + startDate + " in room " + room.getName() + ".\n"
				+ "The doctor's name is " + doctor.getName() + " " + doctor.getSurname() + "and the appointment type is " + doctor.getSpecialty().getType().getName() + ".\n"
				+ "We look forward to seeing you.\nLotus Clinic Staff";
		
		String contentDoctor = "Hello " + doctor.getName() + " " + doctor.getSurname() + "!\nYou have a new appointment.\n"
				+ "The appointment is scheduled for " + startDate + " in room " + room.getName() + ".\n"
				+ "The patient's name is " + patient.getName() + " " + patient.getSurname() + " and the appointment type is " + doctor.getSpecialty().getType().getName()+ ".\n"
				+ "Lotus Clinic Staff";
		
		mailSender.sendMsg(patient.getUsername(), "Appointment notiffication", contentPatient);
		mailSender.sendMsg(doctor.getUsername(), "Appointment notiffication", contentDoctor);
		
		rr.setStatus(RequestStatus.APPROVED);
		requestService.save(rr);
		
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

}
