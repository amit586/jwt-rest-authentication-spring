package com.aerosite.aero.security.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgetRequest {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(max = 10, min = 4)
	private String otp;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	public ForgetRequest() {
	}

	public ForgetRequest(@NotBlank @Email String email, @NotBlank @Size(max = 10, min = 4) String otp,
			@NotBlank @Size(min = 8, max = 100) String password) {
		super();
		this.email = email;
		this.otp = otp;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "ForgetRequest [email=" + email + ", otp=" + otp + ", password=" + password + "]";
	}
}
