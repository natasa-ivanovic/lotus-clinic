package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  MailSender.java
 * Author:  Shejv
 * Purpose: Defines the Class MailSender
 ***********************************************************************/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailSenderModel {
	@Autowired
	public JavaMailSender emailSender;
	
	@Async
	public void sendMsg(String recipient, String subject, String content)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(content);
		emailSender.send(message);
	}
	
	public void sendAdminNotificationAppointment(String recipient, String patient, String type, String doctor) {
		String content = "Hello\nYou have a new appointment request pending for room assignation.\nPatient with the name " + patient 
				+ "requested an appointment of type " + type + ", for doctor " + doctor + ".\nLotus Clinic Staff";
		sendMsg(recipient, "New request pending", content);
	}
}