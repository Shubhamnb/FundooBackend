package com.user.micro.dto;

public class PasswordDto {
	private String newPassword;
	private String confNewPassword;
	
	
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfNewPassword() {
		return confNewPassword;
	}
	public void setConfNewPassword(String confNewPassword) {
		this.confNewPassword = confNewPassword;
	}
	
	
	@Override
	public String toString() {
		return "PasswordDto [newPassword=" + newPassword + ", confNewPassword=" + confNewPassword + "]";
	}
	
}