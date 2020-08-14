package com.aerosite.aero.security.rest;

import com.aerosite.aero.security.jwt.TokenProvider;
import com.aerosite.aero.security.model.ERole;
import com.aerosite.aero.security.model.Role;
import com.aerosite.aero.security.model.User;
import com.aerosite.aero.security.rest.dto.LoginDto;
import com.aerosite.aero.security.rest.dto.SignupRequest;
import com.aerosite.aero.security.rest.dto.ActivationRequest;
import com.aerosite.aero.security.rest.dto.ForgetRequest;
import com.aerosite.aero.security.rest.dto.JwtResponse;
import com.aerosite.aero.security.rest.dto.MessageResponse;
import com.aerosite.aero.security.service.EmailSenderService;
import com.aerosite.aero.security.service.ForgetPasswordService;
import com.aerosite.aero.security.service.OTPProvider;
import com.aerosite.aero.security.service.RoleService;
import com.aerosite.aero.security.service.UserDetailsImpl;
import com.aerosite.aero.security.service.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

	private final TokenProvider tokenProvider;

	private String ACTIVATION = "activation";
	private String USECASE = "usecase";

	@Value("${domain.name}")
	private String domain;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	OTPProvider otpProvider;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ForgetPasswordService forgetPasswordService;

	@Autowired
	private EmailSenderService emailSenderService;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public AuthenticationRestController(TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());

		User user = userDetailsService.findByUsername(loginDto.getUsername()).get();
		if (user.isActivated() == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("User is disabled"));
		}

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userDetailsService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userDetailsService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(), signUpRequest.getLastname(),
				false);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleService.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);

		String jwt = tokenProvider.createTemporaryToken(signUpRequest.getEmail(), ACTIVATION);
		String message = "To confirm your account, please click here : ";
		String link =  domain + "/api/auth/activate?token=" + jwt;

		String result = emailSenderService.sendEmail(signUpRequest.getEmail(), "Please verify your account", message,link);

		userDetailsService.save(user);
		if(result.equals("success"))
			return ResponseEntity.ok(new MessageResponse("Validation is required!, The verification link will be valid for 1 hour"));
		else
			return ResponseEntity.badRequest().body(new MessageResponse("An error occured"));
	}

	@PostMapping("/activate")
	public ResponseEntity<?> sentTokenForActivation(@Valid @RequestBody ActivationRequest activationRequest) {

		String jwt = tokenProvider.createTemporaryToken(activationRequest.getEmail(), ACTIVATION);
		String message = "To activate your account, please click here : " ;
		String link =  domain + "/api/auth/activate?token=" + jwt;
		String result = emailSenderService.sendEmail(activationRequest.getEmail(), "Please activate your account", message,link);
		if(result.equals("success"))
			return ResponseEntity.ok(new MessageResponse("Verification Link sent to your Email"));
		else
			return ResponseEntity.badRequest().body(new MessageResponse("An error occured"));
	}

	@GetMapping("/activate")
	public ResponseEntity<?> registerUser(@RequestParam("token") String token) {
		// activate the user on valid token
		if (tokenProvider.validateToken(token) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:Invalid Token"));
		}
		Claims claim = tokenProvider.getJwtToken(token);
		String email = claim.getSubject();
		String use = claim.get(USECASE).toString();

		if (userDetailsService.existsByEmail(email) == false || use.equals(ACTIVATION) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Register to activate your account"));
		}

		userDetailsService.activateUser(true, email);
		return ResponseEntity.ok(new MessageResponse("User Activated!"));
	}

	@GetMapping("/reset-password")
	public ResponseEntity<?> sendTokenForPasswordReset(@RequestParam("email") String email) {

		String otp = otpProvider.generateOTP();

		forgetPasswordService.saveOTP(email, otp);
		String result = emailSenderService.sendEmail(email, "OTP for resetting password","OTP for resetting password", otp);
		if(result.equals("success"))
			return ResponseEntity.ok(new MessageResponse("OTP Sent To Your Email, Expires in 10 minutes"));
		else
			return ResponseEntity.badRequest().body(new MessageResponse("An error occured"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> sendTokenForPasswordReset(@Valid @RequestBody ForgetRequest forgetRequest) {

		if (!forgetPasswordService.validateOTP(forgetRequest.getEmail(), forgetRequest.getOtp())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Invlid Token"));
		}

		String encodedPassword = encoder.encode(forgetRequest.getPassword());
		userDetailsService.updatePassword(encodedPassword, forgetRequest.getEmail());
		return ResponseEntity.ok(new MessageResponse("Password Reset"));
	}
}
