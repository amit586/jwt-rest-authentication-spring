package com.aerosite.aero.security.rest.dto;

import javax.validation.constraints.Size;


public class UserRequest {
	

	@Size(min = 4, max = 50)
	private String firstname;

	@Size(min = 4, max = 50)
	private String lastname;


	@Size(min = 6, max = 40)
	private String password;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
