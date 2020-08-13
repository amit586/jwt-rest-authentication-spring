package com.aerosite.aero.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aerosite.aero.security.model.ForgetPassword;

@Repository
@Transactional
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Long> {
	Optional<ForgetPassword> findByEmail(String email);
	
	@Modifying
	@Query("update ForgetPassword f set f.otp = ?1 where f.email = ?2")
	int updateOTP(String otp, String email);
}
