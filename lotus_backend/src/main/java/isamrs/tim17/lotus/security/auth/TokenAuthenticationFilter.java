package isamrs.tim17.lotus.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.security.TokenUtils;

//Filter koji ce presretati svaki zahtev klijenta ka serveru
//Sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);

		if (authToken != null) {
			// uzmi username iz tokena
			username = tokenUtils.getUsernameFromToken(authToken);

			if (username != null) {
				// uzmi user-a na osnovu username-a
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				User u = (User) userDetails;
				// ako je menjao pass, okej je
				// ako nije promenio pass, a nije pacijent, dopusti samo ako pristupa stranici za promenu pass
				if (u.getLastPasswordResetDate() != null
						|| (!u.getRole().equals("PATIENT") && u.getLastPasswordResetDate() == null
								&& request.getRequestURI().equals("/auth/change-password"))) {
					// proveri da li je prosledjeni token validan
					if (tokenUtils.validateToken(authToken, userDetails)) {
						// kreiraj autentifikaciju
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}

		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}

}