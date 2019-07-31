package com.bridge.api.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridge.api.model.Email;
import com.bridge.api.model.User;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${fundoo.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Email email) {
		rabbitTemplate.convertAndSend(exchange, routingkey, email);
		System.out.println("Send msg = " + email);
	    
	}
}