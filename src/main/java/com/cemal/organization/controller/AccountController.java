package com.cemal.organization.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cemal.organization.model.AuthToken;
import com.cemal.organization.model.ConfirmationToken;
import com.cemal.organization.model.LoginUser;
import com.cemal.organization.model.RegistrationRequest;
import com.cemal.organization.model.Role;
import com.cemal.organization.model.TokenResponse;
import com.cemal.organization.model.User;
import com.cemal.organization.repository.ConfirmationTokenRepository;
import com.cemal.organization.repository.RoleRepository;
import com.cemal.organization.repository.UserRepo;
import com.cemal.organization.security.TokenProvider;
import com.cemal.organization.service.impl.EmailSenderService;
import com.cemal.organization.service.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/token")
public class AccountController {
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RoleRepository roleRepo;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TokenResponse> login(@RequestBody LoginUser request) throws AuthenticationException {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		return ResponseEntity.ok(new TokenResponse(request.getUsername(), token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Boolean> register(@RequestBody User user) throws AuthenticationException {
		
		User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
		//User user1=new User();
		if (existingUser != null) {
			return ResponseEntity.ok(false);
		} else {
			Role role =new Role();
		/*	role.setId(5L);
			role.setName("USER");
			role.setDescription("User role");*/
			System.out.println(""+role.getName());
		/*	Role adminRole = roleRepo.findByName("USER");
			user.setRole(Arrays.asList(adminRole));  */
			Boolean response = userService.register(user);
			
			
		//	user.setRole((Collection<Role>) role);
                
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(authentication);
			//Boolean response = userService.register(user);
			ConfirmationToken confirmationToken = new ConfirmationToken(user,token);
		    confirmationTokenRepository.save(confirmationToken);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("chand312902@gmail.com");
			mailMessage.setText("To confirm your account, please click here : "
					+ "http://localhost:8002/api/token/confirm-account?token=" + confirmationToken.getConfirmationToken());

			emailSenderService.sendEmail(mailMessage);
		//Boolean response = userService.register(user);
			return ResponseEntity.ok(response);

		
		}
	}



	@RequestMapping(value = "/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<Boolean> confirmUserAccount(		@RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user1 = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
			user1.setEnabled(true);
			userRepository.save(user1);
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}

	}

}