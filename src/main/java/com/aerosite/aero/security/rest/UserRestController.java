package com.aerosite.aero.security.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aerosite.aero.security.model.User;
import com.aerosite.aero.security.rest.dto.MessageResponse;
import com.aerosite.aero.security.rest.dto.UserRequest;
import com.aerosite.aero.security.service.RoleService;
import com.aerosite.aero.security.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api")
public class UserRestController {

	private final UserDetailsServiceImpl userService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleService rolesService;

	public UserRestController(UserDetailsServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public ResponseEntity<UserDetails> getActualUser() {
		return ResponseEntity.ok(userService.getUserWithAuthorities());
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest userRequest)
	{
		User user= userService.findByUsername(userService.getUserWithAuthorities().getUsername().toString()).get();
		
		if(userRequest.getFirstname().isBlank()==false)
		{
			user.setFirstname(userRequest.getFirstname());
		}
		
		if(userRequest.getLastname().isBlank()==false) {
			user.setLastname(userRequest.getLastname());
		}
		
		if(userRequest.getPassword().isBlank()==false) {
			user.setPassword(encoder.encode(userRequest.getPassword()));
		}				
		
		userService.updateUser(user);
		
		return ResponseEntity.ok(new MessageResponse("Your details Updated!"));
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(){
		userService.deleteUser(userService.getUserWithAuthorities().getUsername().toString());
		return ResponseEntity.ok(new MessageResponse("Your Account is deleted"));
	}

}
