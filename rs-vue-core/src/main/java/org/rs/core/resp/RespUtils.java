package org.rs.core.resp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.rs.core.security.authentication.PasswordDecryptException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

public class RespUtils {

	public static Map<String,Object> writeOk(){
		
		Map<String,Object> result = new LinkedHashMap<>();
		
		result.put("error_code", 0);
		result.put("error_desc", "OK");
		return result;
	}
	
	public static Map<String,Object> writeAuthError(Throwable ex){
		
		Map<String,Object> result = new HashMap<>();
		
		result.put("error_desc", ex.getMessage());
		if( ex instanceof InsufficientAuthenticationException ) {
			
			result.put("error_code", RespError.ERR_NO_AUTH);
		}else if(ex instanceof AccessDeniedException ){
			
			result.put("error_code", RespError.ERR_NO_ACCESS);
		}else if(ex instanceof NoHandlerFoundException ){
			
			result.put("error_code", RespError.ERR_NO_HANDLE);
		}else if(ex instanceof HttpRequestMethodNotSupportedException ){
			
			result.put("error_code", RespError.ERR_NO_METHOD);
		}else if(ex instanceof BadCredentialsException ){
			
			result.put("error_code", RespError.ERR_BAD_CREDENTIALS);
		}else if(ex instanceof UsernameNotFoundException ){
			
			result.put("error_code", RespError.ERR_NO_USER);
		}else if(ex instanceof InternalAuthenticationServiceException ){
			
			result.put("error_code", RespError.ERR_AUTH_EXCEPTION);
		}else if(ex instanceof SessionAuthenticationException ){
			
			result.put("error_code", RespError.ERR_SESSION);
		}else if(ex instanceof PasswordDecryptException ){
			
			result.put("error_code", RespError.ERR_DECRYPT_PASSWORD);
		}else if(ex instanceof DuplicateKeyException ){
			
			result.put("error_desc", ex.getCause().getMessage());
			result.put("error_code", RespError.ERR_DuplicateKey);
		}else if(ex instanceof DataIntegrityViolationException ){
			
			result.put("error_desc", ex.getCause().getMessage());
			result.put("error_code", RespError.ERR_DB);
		}else {
			
			result.put("error_code", RespError.ERR_UNKNOWN);
		}		
		return result;
	}
}
