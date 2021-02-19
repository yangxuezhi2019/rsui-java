package org.rs.core.security;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rs.core.resp.RespUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RsAccessDeniedHandler implements AccessDeniedHandler{

	@Autowired
	private ObjectMapper jsonMapper;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Map<String,Object> result = RespUtils.writeAuthError(accessDeniedException);
		String resultStr = jsonMapper.writeValueAsString(result);
		response.getWriter().print(resultStr);
		response.flushBuffer();
	}
}
