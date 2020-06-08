package isamrs.tim17.lotus.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.UserDTO;
import isamrs.tim17.lotus.dto.UserTokenState;
import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.RegistrationRequest;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.security.CustomUserDetailsService;
import isamrs.tim17.lotus.security.TokenUtils;
import isamrs.tim17.lotus.security.auth.JwtAuthenticationRequest;
import isamrs.tim17.lotus.service.AuthorityService;
import isamrs.tim17.lotus.service.RequestService;
import isamrs.tim17.lotus.service.UserService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private RequestService requestService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		String username = authenticationRequest.getUsername();
		User u = userService.findByUsername(username);
		if (u == null) {
			return new ResponseEntity<>("User with username " + username + " doesn't exist!", HttpStatus.BAD_REQUEST);
		}
		if (!u.isEnabled()) {
			return new ResponseEntity<>("Your account hasn't been activated yet. Please check your email.", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Invalid password!", HttpStatus.BAD_REQUEST);
		}
		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getRole()));
	}
		
	/**
	 * This method is used for adding a patient.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code along with the
	 *         current patients.
	 */
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody UserDTO p) {
		User existUser = this.userService.findByUsername(p.getUsername());
		if (existUser != null) {
			return new ResponseEntity<>("User with that username already exists", HttpStatus.BAD_REQUEST);
		}
		if (p.isEmpty()) {
			System.out.println("Error in patient!");
			return new ResponseEntity<>("Not all fields are filled", HttpStatus.BAD_REQUEST);
		}
		Patient patient = new Patient(p);
		patient.setEnabled(false);
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		ArrayList<Authority> auth = new ArrayList<Authority>();
		auth.add(authorityService.findByName("ROLE_PATIENT"));
		patient.setAuthorities(auth);
		userService.save(patient);
		RegistrationRequest req = new RegistrationRequest(patient);
		requestService.save(req);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN','CENTRE_ADMIN','NURSE')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		if (passwordChanger.newPassword.length() < 5) {
			return new ResponseEntity<>("New password must be at least 5 characters!", HttpStatus.BAD_REQUEST);
		}
		String user = userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		if (user.equals("error")) {
			return new ResponseEntity<>("Server error has occurred. Please try again later.", HttpStatus.BAD_REQUEST);
		}
		if (user.equals("pass")) {
			return new ResponseEntity<>("Incorrect old password. Please try again.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
		
}
