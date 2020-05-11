package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  MailSender.java
 * Author:  Shejv
 * Purpose: Defines the Class MailSender
 ***********************************************************************/

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderModel {
	@Autowired
	public JavaMailSender emailSender;
	
	public void sendMsg(String recipient, String subject, String conent)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(conent);
		emailSender.send(message);
	}
}