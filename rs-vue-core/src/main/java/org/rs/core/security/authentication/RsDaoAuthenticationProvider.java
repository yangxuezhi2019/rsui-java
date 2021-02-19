package org.rs.core.security.authentication;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class RsDaoAuthenticationProvider extends DaoAuthenticationProvider{

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		
		return RsAuthenticationToken.createSuccessAuthentication(principal,authentication,user);
	}
}
