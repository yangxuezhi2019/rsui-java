package org.rs.core.utils;

import org.rs.core.security.authentication.RsLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	
	public static RsLoginUser getLoginUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if( authentication != null ) {
			
			Object principal = authentication.getPrincipal();
			if( principal instanceof RsLoginUser ) {
				
				return (RsLoginUser)principal;
			}
		}
		return null;
	}
	
	public static Authentication getAuthentication() {
		
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
