package org.rs.core.security.configurers;

import javax.servlet.http.HttpServletRequest;

import org.rs.core.security.filter.RsAuthenticationFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RsFormLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
	AbstractHttpConfigurer<RsFormLoginConfigurer<H>, H> {

	private RsAuthenticationFilter authFilter;
	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/auth/login", "POST");
	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
	//private AuthenticationEntryPoint authenticationEntryPoint;
	
	public RsFormLoginConfigurer(AuthenticationEntryPoint authenticationEntryPoint,
			AuthenticationSuccessHandler successHandler,
			AuthenticationFailureHandler failureHandler) {
		
		//this.authenticationEntryPoint = authenticationEntryPoint;
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
	}

	@Override
	public void init(H http) throws Exception {
		
		ApplicationContext ctx = http.getSharedObject(ApplicationContext.class);
		ObjectMapper jsonMapper = ctx.getBean(ObjectMapper.class);
		authFilter = new RsAuthenticationFilter(jsonMapper);
		authFilter.setRequiresAuthenticationRequestMatcher(requestMatcher);
		
//		@SuppressWarnings("unchecked")
//		ExceptionHandlingConfigurer<H> exceptionHandling = http.getConfigurer(ExceptionHandlingConfigurer.class);
//		if (exceptionHandling == null) {
//			return;
//		}
//		exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
	}

	@Override
	public void configure(H http) throws Exception {
		
		PortMapper portMapper = http.getSharedObject(PortMapper.class);
		if (portMapper != null) {
			//authenticationEntryPoint.setPortMapper(portMapper);
		}

//		RequestCache requestCache = http.getSharedObject(RequestCache.class);
//		if (requestCache != null) {
//			this.defaultSuccessHandler.setRequestCache(requestCache);
//		}

		authFilter.setAuthenticationManager(http
				.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationSuccessHandler(successHandler);
		authFilter.setAuthenticationFailureHandler(failureHandler);
		if (authenticationDetailsSource != null) {
			authFilter.setAuthenticationDetailsSource(authenticationDetailsSource);
		}
		SessionAuthenticationStrategy sessionAuthenticationStrategy = http
				.getSharedObject(SessionAuthenticationStrategy.class);
		if (sessionAuthenticationStrategy != null) {
			authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
		}
		RememberMeServices rememberMeServices = http
				.getSharedObject(RememberMeServices.class);
		if (rememberMeServices != null) {
			authFilter.setRememberMeServices(rememberMeServices);
		}
		RsAuthenticationFilter filter = postProcess(authFilter);
		http.addFilter(filter);
	}	
}
