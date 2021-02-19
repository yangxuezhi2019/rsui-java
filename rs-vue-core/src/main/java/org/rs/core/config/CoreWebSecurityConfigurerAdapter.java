package org.rs.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.rs.core.security.RsAccessDeniedHandler;
import org.rs.core.security.RsAuthenticationEntryPoint;
import org.rs.core.security.RsAuthenticationFailureHandler;
import org.rs.core.security.RsAuthenticationSuccessHandler;
import org.rs.core.security.RsAuthenticationTrustResolver;
import org.rs.core.security.RsSessionAuthenticationStrategy;
import org.rs.core.security.RsLogoutSuccessHandler;
import org.rs.core.security.RsSecurityContextRepository;
import org.rs.core.security.configurers.RsFormLoginConfigurer;
import org.rs.core.security.configurers.RsUrlAuthorizationConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration(proxyBeanMethods = false)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
//@EnableGlobalMethodSecurity(prePostEnabled=false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CoreWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
	
	private RsAccessDeniedHandler accessDeniedHandler = new RsAccessDeniedHandler();
	private AuthenticationSuccessHandler successHandler = new RsAuthenticationSuccessHandler();
	private AuthenticationFailureHandler failureHandler = new RsAuthenticationFailureHandler();
	private AuthenticationEntryPoint authEntryPoint = new RsAuthenticationEntryPoint();
	private LogoutSuccessHandler logoutHandler = new RsLogoutSuccessHandler();
	private SessionRegistry sessionRegistry;
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;
	//private Environment env;
	
	public CoreWebSecurityConfigurerAdapter( Environment env ) {
		super(true);
		//this.env = env;
	}
	
	@Bean
	public AuthenticationTrustResolver trustResolver() {
		return new RsAuthenticationTrustResolver();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(WebSecurity web) throws Exception {
		
		web.debug(false);
		ApplicationContext ctx = this.getApplicationContext();
		ObjectPostProcessor<Object> objectPostProcessor = ctx.getBean(ObjectPostProcessor.class);
		FindByIndexNameSessionRepository<?> repository = ctx.getBean(FindByIndexNameSessionRepository.class);
		sessionRegistry = new SpringSessionBackedSessionRegistry<>(repository);
		
		objectPostProcessor.postProcess(authEntryPoint);
		objectPostProcessor.postProcess(successHandler);
		objectPostProcessor.postProcess(failureHandler);
		objectPostProcessor.postProcess(logoutHandler);
		objectPostProcessor.postProcess(accessDeniedHandler);
		
		List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<>();
		ChangeSessionIdAuthenticationStrategy changeSessionIdAuthenticationStrategy 
			= new ChangeSessionIdAuthenticationStrategy();
		objectPostProcessor.postProcess(changeSessionIdAuthenticationStrategy);
		
		RsSessionAuthenticationStrategy concurrentSessionControlStrategy 
			= new RsSessionAuthenticationStrategy(ctx,repository,sessionRegistry);
		objectPostProcessor.postProcess(concurrentSessionControlStrategy);
		
		delegateStrategies.addAll(Arrays.asList(concurrentSessionControlStrategy,changeSessionIdAuthenticationStrategy));		
		sessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(delegateStrategies);
		web.setSharedObject(SessionRegistry.class, sessionRegistry);
		super.init(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		ApplicationContext ctx = getApplicationContext();
	    RsSecurityContextRepository httpSecurityRepository = new RsSecurityContextRepository(ctx);
	    httpSecurityRepository.setAllowSessionCreation(true);
	    httpSecurityRepository.setDisableUrlRewriting(true);
	    httpSecurityRepository.setTrustResolver((AuthenticationTrustResolver)ctx.getBean(AuthenticationTrustResolver.class));
	    http.setSharedObject(SecurityContextRepository.class, httpSecurityRepository);
		
		http.setSharedObject(RequestCache.class, new NullRequestCache());
		RsUrlAuthorizationConfigurer<HttpSecurity> authorizationConfigurer = new RsUrlAuthorizationConfigurer<>();
		
		http
		.apply(authorizationConfigurer).and()
		.csrf().disable()
		.addFilter(new WebAsyncManagerIntegrationFilter())
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authEntryPoint).and()
		.headers().and()
		.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy).sessionAuthenticationFailureHandler(failureHandler).and()
		.securityContext().and()
		.requestCache().and()
		.anonymous().and()
		.servletApi().and()
		.apply(new RsFormLoginConfigurer<>(authEntryPoint,successHandler,failureHandler)).and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
		.logoutSuccessHandler(logoutHandler);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//auth.ldapAuthentication();
		super.configure(auth);
	}

	
}
