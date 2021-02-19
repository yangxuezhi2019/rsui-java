package org.rs.core.security;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rs.core.resp.RespUtils;
import org.rs.core.utils.CoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.util.RedirectUrlBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RsAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(RsAuthenticationEntryPoint.class);
	@Autowired
	private ObjectMapper jsonMapper;
	private PortResolver portResolver = new PortResolverImpl();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("RsAuthenticationEntryPoint", authException);
		}
		
		if(CoreUtils.isJsonRequest(request) || CoreUtils.isAjaxRequest(request)) {
			
			Map<String,Object> result = RespUtils.writeAuthError(authException);
			String resultStr = jsonMapper.writeValueAsString(result);
			response.getWriter().print(resultStr);
			response.flushBuffer();
		}else {
			
			int serverPort = portResolver.getServerPort(request);
			String scheme = request.getScheme();
			RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

			urlBuilder.setScheme(scheme);
			urlBuilder.setServerName(request.getServerName());
			urlBuilder.setPort(serverPort);
			urlBuilder.setContextPath(request.getContextPath());
			urlBuilder.setPathInfo("/");
			
			String url = urlBuilder.getUrl();
			response.sendRedirect(url);
		}
	}
}
