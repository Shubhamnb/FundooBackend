package com.user.micro.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.user.micro.model.Email;

@Component
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	//@RabbitListener(queues = "${fundoo.rabbitmq.queue}")
	public void send(Email email,String id) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(email.getTo());
		helper.setText("http://localhost:8087/users/emailvalidation?id=" + id);
		//helper.setText(email.getBody());
		helper.setSubject("Hi");
		System.out.println("MAIL Sender");
		javaMailSender.send(message);

	}

	public void sendFogetPassWordLink(Email email, String token) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(email.getTo());
		helper.setText("http://localhost:4200/resetpassword/"+token);
		helper.setSubject(email.getSubject());

		javaMailSender.send(message);

	}

}
