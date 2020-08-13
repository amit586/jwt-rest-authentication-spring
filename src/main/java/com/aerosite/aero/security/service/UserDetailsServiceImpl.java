package com.aerosite.aero.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aerosite.aero.security.model.User;
import com.aerosite.aero.security.repository.UserRepository;
import com.aerosite.aero.security.SecurityUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public UserDetails getUserWithAuthorities() {
		String username = SecurityUtils.getCurrentUsername().get();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public void save(User user) {
		userRepository.save(user);

	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void activateUser(boolean activated, String email) {
		userRepository.activateUser(activated, email);
	}

	public void updatePassword(String encodedPassword, String email) {
		userRepository.updatePassword(encodedPassword, email);
	}

	public void updateUser(User user) {
		userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getPassword());

	}

	public void deleteUser(String username) {
		userRepository.deleteByUsername(username);
	}

}
