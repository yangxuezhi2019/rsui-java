package org.rs.core.security;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rs.core.resp.RespUtils;
import org.rs.core.utils.CoreUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.RedirectUrlBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RsInvalidSessionStrategy implements InvalidSessionStrategy{

	private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private PortResolver portResolver = new PortResolverImpl();
	private ObjectMapper jsonMapper;
	public RsInvalidSessionStrategy( ObjectMapper jsonMapper) {
		
		this.jsonMapper = jsonMapper;
	}
	
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		if(CoreUtils.isJsonRequest(request) || CoreUtils.isAjaxRequest(request)) {
			
			Map<String,Object> result = RespUtils.writeAuthError(new InsufficientAuthenticationException(
					messages.getMessage(
						"RsInvalidSessionStrategy.insufficientAuthentication",
						"Invalid Session,Full authentication is required to access this resource")));
			String resultStr = jsonMapper.writeValueAsString(result);
			response.getWriter().print(resultStr);
			response.flushBuffer();
		}else {
			
			//String reqUrl = UrlUtils.buildRequestUrl(request);
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
