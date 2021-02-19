package org.rs.core.security.authentication;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class PasswordDecryptException extends AuthenticationException{

	public PasswordDecryptException(String msg) {
		super(msg);
	}
	
	public PasswordDecryptException(String msg, Throwable t) {
		super(msg, t);
	}

}
