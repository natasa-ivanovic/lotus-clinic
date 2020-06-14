package isamrs.tim17.lotus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.Request;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.model.Vacation;
import isamrs.tim17.lotus.model.VacationRequest;

@Component
public class MailSenderService {
	
	@Autowired
	public JavaMailSender emailSender;

	
	public void sendMsg(String recipient, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(content);
		emailSender.send(message);
	}

	@Async
	public void sendAdminNotificationAppointment(String recipient, String patient, String type, String doctor) {
		String content = "Hello\nYou have a new appointment request pending for room assignation.\nPatient with the name "
				+ patient + "requested an appointment of type " + type + ", for doctor " + doctor
				+ ".\nLotus Clinic Staff";
		sendMsg(recipient, "Lotus Clinic - New request pending", content);
	}
	
	@Async
	public void sendVacationAccept(VacationRequest request, String username) {
		String content = "Hello\nYour request for a vacation from " + request.getStartDate() + " to " + request.getEndDate() + " was approved.\nLotus Clinic Staff";
		sendMsg(username, "Lotus Clinic - Vacation", content);
	}	
	
	@Async
	public void sendVacationDecline(VacationRequest request, String username) {
		String content = "Hello\nYour request for a vacation from " + request.getStartDate() + " to " + request.getEndDate() + " was declined.\nLotus Clinic Staff";
		sendMsg(username, "Lotus Clinic - Vacation", content);
	}

	@Async
	public void sendPatientRequest(Appointment app) {
		String content = "Hello " + app.getMedicalRecord().getPatient().getName() + " "
				+ app.getMedicalRecord().getPatient().getSurname()
				+ "!\nIn response to your appointment request, we have created a term for you in our centre.\n"
				+ "The appointment is scheduled for " + app.getStartDate() + " in room " + app.getRoom().getName()
				+ ", at our clinic " + app.getClinic().getName() + ".\n" + "The clinic is located at "
				+ app.getClinic().getAddress() + ".\n" + "The doctor's name is " + app.getDoctor().getName() + " "
				+ app.getDoctor().getSurname() + "and the appointment type is "
				+ app.getDoctor().getSpecialty().getType().getName() + ".\n" + "The appointment price is "
				+ String.format("%.2f", app.getPrice()) + " RSD.\n"
				+ "Please confirm the appointment by visiting the following link: https://lotus-clinic.herokuapp.com/confirm/"
				+ app.getRegKey() + " \nWe look forward to seeing you.\nLotus Clinic Staff";
		sendMsg(app.getMedicalRecord().getPatient().getUsername(), "Lotus Clinic - Appointment request confirmation",
				content);
	}

	@Async
	public void sendPatientNotification(Appointment app) {
		String content = "Hello " + app.getMedicalRecord().getPatient().getName() + " "
				+ app.getMedicalRecord().getPatient().getSurname()
				+ "!\nDuring an appointment, the doctors requested a new appointment for you.\n"
				+ "The appointment is scheduled for " + app.getStartDate() + " in room " + app.getRoom().getName()
				+ ", at our clinic " + app.getClinic().getName() + ".\n" + "The clinic is located at "
				+ app.getClinic().getAddress() + ".\n" + "The doctor's name is " + app.getDoctor().getName() + " "
				+ app.getDoctor().getSurname() + " and the appointment type is "
				+ app.getDoctor().getSpecialty().getType().getName() + ".\n" + "The appointment price is "
				+ String.format("%.2f", app.getPrice()) + " RSD.\n"
				+ "We look forward to seeing you.\nLotus Clinic Staff";
		sendMsg(app.getMedicalRecord().getPatient().getUsername(), "Lotus Clinic - Appointment scheduled", content);
	}

	@Async
	public void sendDoctorNotification(Appointment app) {
		String content = "Hello " + app.getDoctor().getName() + " " + app.getDoctor().getSurname()
				+ "!\nYou have a new appointment.\n" + "The appointment is scheduled for " + app.getStartDate()
				+ " in room " + app.getRoom().getName() + ".\n" + "The patient's name is "
				+ app.getMedicalRecord().getPatient().getName() + " " + app.getMedicalRecord().getPatient().getSurname()
				+ " and the appointment type is " + app.getDoctor().getSpecialty().getType().getName() + ".\n"
				+ "Lotus Clinic Staff";
		sendMsg(app.getDoctor().getUsername(), "Lotus Clinic - Appointment scheduled", content);

	}

	@Async
	public void sendDoctorCanceledAppointment(Appointment app) {
		String message = "Hello " + app.getDoctor().getName() + " " + app.getDoctor().getSurname()
				+ "!\nAn existing appointment has been canceled.\n" + "The appointment was scheduled for "
				+ app.getStartDate() + ".\n" + "The patient's name is " + app.getMedicalRecord().getPatient().getName()
				+ " " + app.getMedicalRecord().getPatient().getSurname() + " and the appointment type is "
				+ app.getDoctor().getSpecialty().getType().getName() + ".\n" + "Your work calendar has been updated.\n"
				+ "Lotus Clinic Staff";
		sendMsg(app.getMedicalRecord().getPatient().getUsername(), "Lotus Clinic - Appointment canceled", message);

	}

	@Async
	public void sendPatientCanceledAppointment(Appointment app) {
		String message = "Hello " + app.getMedicalRecord().getPatient().getName() + " "
				+ app.getMedicalRecord().getPatient().getName() + "!\nAn existing appointment has been canceled.\n"
				+ "The appointment was scheduled for " + app.getStartDate() + ".\n" + "The doctor's name is "
				+ app.getDoctor().getName() + " " + app.getDoctor().getSurname() + " and the appointment type is "
				+ app.getDoctor().getSpecialty().getType().getName() + ".\n" + "Lotus Clinic Staff";
		sendMsg(app.getMedicalRecord().getPatient().getUsername(), "Lotus Clinic - Appointment canceled", message);
	}

	@Async
	public void sendPatientPremadeScheduled(Appointment app) {
		String finalPrice = String.format("%.2f", app.getPrice() * (100 - app.getDiscount()) / 100);
		String message = "Hello " + app.getMedicalRecord().getPatient().getName() + " "
				+ app.getMedicalRecord().getPatient().getSurname()
				+ "!\nYou have scheduled an appointment using our premade appointment feature.\n"
				+ "The appointment is scheduled for " + app.getStartDate() + " in room " + app.getRoom().getName()
				+ ", at our clinic " + app.getClinic().getName() + ".\n" + "The clinic is located at "
				+ app.getClinic().getAddress() + ".\n" + "The doctor's name is " + app.getDoctor().getName() + " "
				+ app.getDoctor().getSurname() + "and the appointment type is "
				+ app.getDoctor().getSpecialty().getType().getName() + ".\n" + "The appointment price is "
				+ String.format("%.2f", app.getPrice()) + " RSD and you get a "
				+ String.format("%.2f", app.getDiscount()) + "% discount for using our premade function, totalling "
				+ finalPrice + " RSD.\n" + "We look forward to seeing you.\nLotus Clinic Staff";
		sendMsg(app.getMedicalRecord().getPatient().getUsername(), "Lotus Clinic - Appointment scheduled", message);

	}

	@Async
	public void sendPatientInvalidRegistration(String username, String message) {
		String content = "Hello\nWe at Lotus Clinic have reviewed your registration request and decided it is invalid.\nThe reason your request was declined is:\n"
				+ message + "\nWe hope we may be of service another time.\nLotus Clinic Staff";
		sendMsg(username, "Lotus Clinic - Registration declined", content);
		
	}

	@Async
	public void sendPatientConfirmRegistration(String username, String key) {
		String content = "Hello\nWe at Lotus Clinic have reviewed your registration request and decided it is valid.\nPlease follow this link to activate your account:\n"
				+ "https://lotus-clinic.herokuapp.com/registrations/" + key + " \nLotus Clinic Staff";
		sendMsg(username, "Lotus Clinic - Confirm registration", content);
		
	}
	
	@Async
	public void sendDoctorOperationCanceled(String recipient, String patient, String type, String doctors,
			String date) {
		String content = "Hello\nOne of your operations has been canceled.\nThe operation was scheduled for " + date
				+ ".\nPatient's name was " + patient + " and the operation type was " + type
				+ ".\nThe doctors that were on this operation are: " + doctors + "\nLotus Clinic Staff";
		sendMsg(recipient, "Operation canceled", content);
	}

}