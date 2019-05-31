package com.bridge.api.service;

import javax.mail.MessagingException;

import com.bridge.api.dto.NoteDto;
import com.bridge.api.dto.UserDto;

public interface Service {
	boolean saveService(UserDto userDto);

	void setVerify(String id);

	String loginService(String userName, String passWord);

	boolean isAvilabe(String email);

	
}
