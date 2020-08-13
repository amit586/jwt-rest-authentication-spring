package com.aerosite.aero.security.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class ActivationRequest {
	@NotNull
	@Email
	@Size(max = 50)
	private String email;

	public ActivationRequest() {

	}

	public ActivationRequest(@Email @Size(max = 50) String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ActivationRequest [email=" + email + "]";
	}
}
