package com.aerosite.aero.security.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "forget_password", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class ForgetPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 10)
	private String otp;

	@UpdateTimestamp
	private LocalDateTime createDateTime;

	public ForgetPassword() {

	}

	public ForgetPassword(@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 10) String otp) {
		super();
		this.email = email;
		this.otp = otp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public String toString() {
		return "ForgetPassword [id=" + id + ", email=" + email + ", otp=" + otp + ", createDateTime=" + createDateTime
				+ "]";
	}

}
