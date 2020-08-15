package com.aerosite.aero.dolby.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aerosite.aero.dobly.dto.ResponseToken;

import kong.unirest.Unirest;

@Service
public class DolbyAuthService {
	@Value("${dolby.consumer.key}")
	private String consumerKey;
	
	@Value("${dolby.consumer.secret}")
	private String consumerSecret;

	@Async
	public ResponseToken getToken() throws Exception {

		ResponseToken responseToken = Unirest.post("https://session.voxeet.com/v1/oauth2/token")
				.basicAuth(consumerKey, consumerSecret).header("Content-Type", "application/json")
				.body("{ \"grant_type\": \"client_credentials\"}")
				.asObject(ResponseToken.class)
				.getBody();
		return responseToken;
	}

}
