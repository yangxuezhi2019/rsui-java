package org.rs.core.security.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.rs.core.security.authentication.PasswordDecryptException;
import org.rs.core.security.authentication.RsAuthenticationToken;
import org.rs.utils.RsaUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RsAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final ObjectMapper jsonMapper;
	
	public RsAuthenticationFilter( ObjectMapper jsonMapper ){
		
		this.jsonMapper = jsonMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String contentType = request.getHeader("Content-Type");
		if ( !request.getMethod().equals("POST") || 
				( contentType != null && !contentType.startsWith("application/json")) ) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		LoginUserInfo loginUserInfo = obtainRequestParam(request);
		if( loginUserInfo == null ) {
			
			throw new AuthenticationServiceException("Authentication exception.");
		}
		
		String username = loginUserInfo.getUsername();
		String password = loginUserInfo.getPassword();
		
		//decrypt password
		if( password != null && !"".equals(password) ) {
			PrivateKey privateKey = RsaUtils.generateRSAPrivateKey(RsaUtils.privateKey);
			byte[] pwdBytes = RsaUtils.decrypt(privateKey, Base64.decodeBase64(password));
			if( pwdBytes == null ) {
				
				throw new PasswordDecryptException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badPassword",
						"Unable to decrypt password"));
			}
			password = StringUtils.newString(pwdBytes, "UTF-8");
		}

		if (username == null) {
			username = "";
		}
		
		if ( password == null ) {
			password = "";
		}

		username = username.trim();

		RsAuthenticationToken authRequest = new RsAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private LoginUserInfo obtainRequestParam(HttpServletRequest request){
		
		LoginUserInfo result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream ins;
		try {
			ins = request.getInputStream();
			int length = 0;
			byte[] buffer = new byte[1024];
			while( (length = ins.read(buffer)) != -1 ) {
				os.write(buffer, 0, length);
			}
			
			String jsonString = new String(os.toByteArray(),"utf-8");
			result = jsonMapper.readValue(jsonString, LoginUserInfo.class);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	static public class LoginUserInfo{
		
		private String username;
		private String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}
}
