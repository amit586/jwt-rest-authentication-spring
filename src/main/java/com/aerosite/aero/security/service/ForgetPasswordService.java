package com.aerosite.aero.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerosite.aero.security.model.ForgetPassword;
import com.aerosite.aero.security.repository.ForgetPasswordRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ForgetPasswordService {
	@Autowired
	ForgetPasswordRepository forgetPasswordRepository;

	public void saveOTP(String email, String otp) {
		if(findByEmail(email).isEmpty()) {
			ForgetPassword forgetPassword = new ForgetPassword(email, otp);
			forgetPasswordRepository.save(forgetPassword);
		}
		else {
			forgetPasswordRepository.updateOTP(otp, email);
		}
	}

	public boolean validateOTP(String email, String otp) {
		Optional<ForgetPassword> forgetPasswordOptional = findByEmail(email);
		if (forgetPasswordOptional.isEmpty() == true) {
//			System.out.println(">>>>> email not found");
			return false;
		}

		ForgetPassword forgetPassword = forgetPasswordOptional.get();

		LocalDateTime currentTime = LocalDateTime.now();

		if (forgetPassword.getCreateDateTime().isAfter(currentTime.plusSeconds(600))) {
//			System.out.println(">>>>> Token expired");
			deleteOTP(forgetPassword);
			return false;
		}

		if (!forgetPassword.getOtp().equals(otp)) {
			System.out.println(
					">>>>> otp not valid otpRecieved {" + otp + "} , otp in db{" + forgetPassword.getOtp() + "}");
			deleteOTP(forgetPassword);
			return false;
		}

		deleteOTP(forgetPassword);
		return true;
	}

	public void deleteOTP(ForgetPassword forgetPassword) {
		forgetPasswordRepository.delete(forgetPassword);
	}

	public Optional<ForgetPassword> findByEmail(String email) {
		return forgetPasswordRepository.findByEmail(email);
	}

}
