package com.user.micro.dto;




public class UserDto {
	
	private String fname;
	private String email;
	private String password;
	private String address;
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "UserDto [fname=" + fname + ", email=" + email + ", password=" + password + ", address=" + address + "]";
	}
	
	
	
	

}
