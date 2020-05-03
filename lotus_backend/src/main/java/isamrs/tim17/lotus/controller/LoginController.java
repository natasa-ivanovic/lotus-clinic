package isamrs.tim17.lotus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isamrs.tim17.lotus.dto.UserTokenState;
import isamrs.tim17.lotus.model.Authority;
import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.security.CustomUserDetailsService;
import isamrs.tim17.lotus.security.TokenUtils;
import isamrs.tim17.lotus.security.auth.JwtAuthenticationRequest;
import isamrs.tim17.lotus.service.AuthorityService;
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
	
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		// 
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getRole()));
	}
	
	@GetMapping("/logout")
	public void logout() {
		
	}
	
	/**
	 * This method is used for adding a patient.
	 * 
	 * @param patient This is a patient object from the HTTP request.
	 * @return ResponseEntity This returns the HTTP status code along with the
	 *         current patients.
	 */
	@PostMapping("/register")
	public ResponseEntity<Patient> register(@RequestBody Patient patient) {
		User existUser = this.userService.findByUsername(patient.getUsername());
		if (existUser != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (isEmptyOrNull(patient)) {
			System.out.println("Error in patient!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		patient.setEnabled(false);
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		ArrayList<Authority> auth = new ArrayList<Authority>();
		auth.add(authorityService.findByName("ROLE_PATIENT"));
		patient.setAuthorities(auth);
		userService.save(patient);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	// U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn, user.getRole()));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
	private boolean isEmptyOrNull(Patient patient) {
		if (patient == null)
			return true;
		if (patient.getName() == null || "".equals(patient.getName()))
			return true;
		if (patient.getSurname() == null || "".equals(patient.getSurname()))
			return true;
		if (patient.getUsername() == null || "".equals(patient.getUsername()))
			return true;
		if (patient.getPassword() == null || "".equals(patient.getPassword()))
			return true;
		if (patient.getAddress() == null || "".equals(patient.getAddress()))
			return true;
		if (patient.getCity() == null || "".equals(patient.getCity()))
			return true;
		if (patient.getCountry() == null || "".equals(patient.getCountry()))
			return true;
		if (patient.getPhoneNumber() == null || "".equals(patient.getPhoneNumber()))
			return true;
		if (patient.getGender() == null || "".equals(patient.getGender().toString()))
			return true;
		if (patient.getBirthDate() == null || "".equals(patient.getBirthDate().toString()))
			return true;
		return false;
	}
	
}
