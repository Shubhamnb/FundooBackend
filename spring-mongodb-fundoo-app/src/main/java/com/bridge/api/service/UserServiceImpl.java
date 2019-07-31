package com.bridge.api.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridge.api.dto.PasswordDto;
import com.bridge.api.dto.UserDto;
import com.bridge.api.model.Email;
import com.bridge.api.model.User;
import com.bridge.api.mongo.reposetory.NoteRepository;
import com.bridge.api.mongo.reposetory.UserRepository;
import com.bridge.api.rabbitmq.RabbitMQSender;
import com.bridge.api.response.ResponseToken;
import com.bridge.api.util.StatusHelper;
import com.bridge.api.util.UserToken;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	MailService mailService;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserToken userToken;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@Autowired
	NoteRepository noteRepository;
	
	@Override
	public boolean saveService(UserDto userDto) {
		String idToken = null;
		Email email = new Email();
		User user = mapper.map(userDto, User.class);
		user.setCurrentDate(LocalDate.now());
		user.setPassWord(passwordEncoder.encode(user.getPassWord()));
		User getuser = userRepository.save(user);
		if(getuser != null) {
			idToken = userToken.generateToken(getuser.getUserId());
			System.out.println(idToken);
		}
		else {
			return false;
		}
		
		email.setFrom("bohari2@gmail.com");
		email.setTo(userDto.getEmail());
		email.setSubject("Email Verification ");
		email.setBody("http://localhost:8080/users/emailvalidation?id=" + idToken);
		try {
			//mailService.send(email, idToken);
			rabbitMQSender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void setVerify(String id) {
		String tokenId = userToken.tokenVerify(id);
		Optional<User> user = userRepository.findById(tokenId);
		User user1 = user.get();
		user1.setVerified(true);
		userRepository.save(user1);
	}

	@Override
	public ResponseToken loginService(String userName, String passWord) {
		Optional<User> user = userRepository.findByEmail(userName);
		
	
		if (user.isPresent()) {
			String userNameToken = userToken.generateToken(user.get().getUserId());
			
			if (passwordEncoder.matches(passWord, user.get().getPassWord())) {
				ResponseToken rsepoToken = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.success"), Integer.parseInt(environment.getProperty("status.success.code")), userNameToken);  
				return rsepoToken;
			}
				
		}
			ResponseToken rsepoToken = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.invalidInput"), Integer.parseInt(environment.getProperty("status.login.errorCode")), " ----- ");
			return rsepoToken;
		
	}

	/*
	 * Method is cheack where sender is present or not	
	*/	@Override
	public boolean isAvilabe(String email) {
		Email emailUser = new Email();
		Optional<User> user = userRepository.findByEmail(email);
		
		
		if(user.isPresent()) {
			String token =userToken.generateToken(user.get().getUserId());
			emailUser.setFrom("bohari2@gmail.com");
			emailUser.setTo(user.get().getEmail());
			emailUser.setSubject("FOrget Password Verification");
			
			try {
				mailService.sendFogetPassWordLink(emailUser, token);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true; 
		}
		return false;
	}

	@Override
	public void resetPassword(PasswordDto passwordDto, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			user.get().setPassWord(passwordEncoder.encode(passwordDto.getNewPassword()));
			User user1 = userRepository.save(user.get());
			System.out.println(user1);
		}else {
			
		}
	}

	
}
