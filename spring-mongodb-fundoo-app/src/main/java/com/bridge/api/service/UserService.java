package com.bridge.api.service;

import javax.mail.MessagingException;

import com.bridge.api.dto.NoteDto;
import com.bridge.api.dto.PasswordDto;
import com.bridge.api.dto.UserDto;
import com.bridge.api.response.ResponseToken;

public interface UserService {
	boolean saveService(UserDto userDto);

	void setVerify(String id);

	ResponseToken loginService(String userName, String passWord);

	boolean isAvilabe(String email);

	void resetPassword(PasswordDto passwordDto, String token);

	
}