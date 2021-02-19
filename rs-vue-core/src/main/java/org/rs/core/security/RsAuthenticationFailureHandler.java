package org.rs.core.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rs.core.resp.RespUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RsAuthenticationFailureHandler implements AuthenticationFailureHandler{

	private static final Logger logger = LoggerFactory.getLogger(RsAuthenticationFailureHandler.class);
	@Autowired
	private ObjectMapper jsonMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		if( logger.isDebugEnabled() ) {
			logger.error("auth failure", exception.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Map<String,Object> result = RespUtils.writeAuthError(exception);
		String resultStr = jsonMapper.writeValueAsString(result);
		response.getWriter().print(resultStr);
		response.flushBuffer();
	}
}
