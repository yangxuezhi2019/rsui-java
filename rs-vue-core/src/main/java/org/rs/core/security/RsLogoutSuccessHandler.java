package org.rs.core.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rs.core.resp.RespUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RsLogoutSuccessHandler implements LogoutSuccessHandler{

	@Autowired
	private ObjectMapper jsonMapper;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Map<String,Object> result = RespUtils.writeOk();
		String resultStr = jsonMapper.writeValueAsString(result);
		response.getWriter().print(resultStr);
		response.flushBuffer();
	}
}
