package com.bridge.api.controller;

import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.client.AmazonClient;
import com.bridge.api.dto.LoginDto;
import com.bridge.api.dto.PasswordDto;
import com.bridge.api.dto.UserDto;
import com.bridge.api.response.Response;
import com.bridge.api.response.ResponseToken;
import com.bridge.api.service.UserService;
import com.bridge.api.util.StatusHelper;

@RestController
@RequestMapping("/users")
@PropertySource("classpath:message.properties")
@CrossOrigin(origins = "*",allowedHeaders = "*",exposedHeaders= {"token"})
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private Environment environment;
	@Autowired
	private AmazonClient amazonClient;
	/**
	 * This endpoint for new user register
	 * @param userDto user payload
	 * @return response.
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> saveUser(@RequestBody UserDto userDto) {
		boolean flag = userService.saveService(userDto);
		if(flag == true) {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.register.success"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.register.emailExistError"),
					Integer.parseInt(environment.getProperty("status.register.errorCode")));
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		
	}
	
//	Proper Response object instead String..
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Map<String, String> map){
		
		return null;
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<String> getById(@PathVariable String id){
		return null;
	}
	
 
	@GetMapping("/emailvalidation")
	public ResponseEntity<Response> emailValidation(@RequestParam String id) {
		userService.setVerify(id);
		Response response  = StatusHelper.statusInfo(environment.getProperty("status.email.verified"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody LoginDto loginDto) {
		System.out.println(loginDto);
		ResponseToken responseToken = userService.loginService(loginDto.getEmail(), loginDto.getPassWord());
		return new ResponseEntity<ResponseToken>(responseToken,HttpStatus.OK);
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestParam String email) {
		System.out.println("SHhubham Bohari :"+email+"Shubham");
		boolean flag = userService.isAvilabe(email);
		if(flag == true) {
			 Response response  = StatusHelper.statusInfo(environment.getProperty("status.forgetPassword.success"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			 return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		else {
			Response response  = StatusHelper.statusInfo(environment.getProperty("status.email.invalidMail"),
					Integer.parseInt(environment.getProperty("status.email.errorCode")));
			 return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
	}
	
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody PasswordDto passwordDto,@PathVariable String token) {
		System.out.println(passwordDto);
		if(passwordDto.getNewPassword().equals(passwordDto.getConfNewPassword())) {
			userService.resetPassword(passwordDto,token);
			 Response response  = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),
						Integer.parseInt(environment.getProperty("status.success.code")));
			 return new ResponseEntity<Response>(response,HttpStatus.OK);
		}else {
			 Response response  = StatusHelper.statusInfo(environment.getProperty("status.passreset.failed"),
						Integer.parseInt(environment.getProperty("status.token.errorCode")));
			 return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getImage")
	public URL getImageUrl(@RequestHeader String token) {
	URL url=amazonClient.getImageUrl(token);
	return url;
	}

	 
}
