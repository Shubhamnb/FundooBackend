package com.user.micro.service;


import com.user.micro.dto.PasswordDto;
import com.user.micro.dto.UserDto;
import com.user.micro.response.ResponseToken;

public interface UserService {
	boolean saveService(UserDto userDto);

	void setVerify(String id);

	ResponseToken loginService(String userName, String passWord);

	boolean isAvilabe(String email);

	void resetPassword(PasswordDto passwordDto, String token);

	
}
