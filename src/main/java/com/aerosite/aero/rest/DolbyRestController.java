package com.aerosite.aero.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aerosite.aero.dobly.dto.ResponseError;
import com.aerosite.aero.dobly.dto.ResponseToken;
import com.aerosite.aero.dolby.service.DolbyAuthService;
import com.aerosite.aero.security.rest.dto.MessageResponse;

import kong.unirest.HttpResponse;

@RestController
@RequestMapping("/api/dolby")
public class DolbyRestController {
	
	@Autowired
	DolbyAuthService dolbyAuthService;
	
	@GetMapping("/token")
	public   ResponseEntity<?> getToken() throws Exception
	{
		ResponseToken responseToken =  dolbyAuthService.getToken();
		System.out.println(responseToken.toString());
		if(responseToken.getExpires_in()>1)
		{
			return ResponseEntity.ok(responseToken);
		}
		else
			return ResponseEntity.badRequest().body(new MessageResponse("an error occured"));
	}
	
}
