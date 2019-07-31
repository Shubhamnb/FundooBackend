package com.user.micro.dto;

public class LoginDto {
	private String email;
	private String passWord;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "LoginDto [email=" + email + ", passWord=" + passWord + "]";
	}
	
	
}
