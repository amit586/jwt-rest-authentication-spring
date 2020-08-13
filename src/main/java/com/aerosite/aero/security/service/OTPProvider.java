package com.aerosite.aero.security.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPProvider {
	public String generateOTP() {

		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String values = Capital_chars + Small_chars + numbers;

		// Using random method
		Random rndm_method = new Random();

		String password = "";

		for (int i = 0; i < 8; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			password += values.charAt(rndm_method.nextInt(values.length()));

		}
		return password;
	}

}
