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
}