package org.rs.core.security.configurers;

import org.rs.core.security.access.RsAccessDecisionManager;
import org.rs.core.security.access.RsSecurityMetadataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class RsUrlAuthorizationConfigurer<H extends HttpSecurityBuilder<H>>
	extends	AbstractHttpConfigurer<RsUrlAuthorizationConfigurer<H>, H> {

	private Boolean filterSecurityInterceptorOncePerRequest;
	private SecurityExpressionHandler<FilterInvocation> expressionHandler;
	private AccessDecisionManager accessDecisionManager;
	
	@Override
	public void configure(H http) throws Exception {
		FilterInvocationSecurityMetadataSource metadataSource = createMetadataSource(http);
		if (metadataSource == null) {
			return;
		}
		FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor(
				http, metadataSource, http.getSharedObject(AuthenticationManager.class));
		if (filterSecurityInterceptorOncePerRequest != null) {
			securityInterceptor
					.setObserveOncePerRequest(filterSecurityInterceptorOncePerRequest);
		}
		securityInterceptor = postProcess(securityInterceptor);
		http.addFilter(securityInterceptor);
		http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);
	}
	
	private FilterInvocationSecurityMetadataSource createMetadataSource(H http) {
		ApplicationContext ctx = http.getSharedObject(ApplicationContext.class);
		return ctx.getBean(RsSecurityMetadataSource.class);
	}
	
	/*private List<AccessDecisionVoter<?>> getDecisionVoters(H http){
		
		List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
		WebExpressionVoter expressionVoter = new WebExpressionVoter();
		expressionVoter.setExpressionHandler(getExpressionHandler(http));
		decisionVoters.add(expressionVoter);
		return decisionVoters;
	}
	
	private AccessDecisionManager createDefaultAccessDecisionManager(H http) {
		AffirmativeBased result = new AffirmativeBased(getDecisionVoters(http));
		return postProcess(result);
	}*/
	
	private AccessDecisionManager getAccessDecisionManager(H http) {
		if (accessDecisionManager == null) {
			accessDecisionManager = new RsAccessDecisionManager(getExpressionHandler(http));
		}
		return accessDecisionManager;
	}
	
	private FilterSecurityInterceptor createFilterSecurityInterceptor(H http,
			FilterInvocationSecurityMetadataSource metadataSource,
			AuthenticationManager authenticationManager) throws Exception {
		
		FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
		securityInterceptor.setSecurityMetadataSource(metadataSource);
		securityInterceptor.setAccessDecisionManager(getAccessDecisionManager(http));
		securityInterceptor.setAuthenticationManager(authenticationManager);
		securityInterceptor.afterPropertiesSet();
		return securityInterceptor;
	}
	
	protected SecurityExpressionHandler<FilterInvocation> getExpressionHandler(H http) {
		if (expressionHandler == null) {
			DefaultWebSecurityExpressionHandler defaultHandler = new DefaultWebSecurityExpressionHandler();
			AuthenticationTrustResolver trustResolver = http.getSharedObject(AuthenticationTrustResolver.class);
			if (trustResolver != null) {
				defaultHandler.setTrustResolver(trustResolver);
			}
			ApplicationContext context = http.getSharedObject(ApplicationContext.class);
			if (context != null) {
				String[] roleHiearchyBeanNames = context.getBeanNamesForType(RoleHierarchy.class);
				if (roleHiearchyBeanNames.length == 1) {
					defaultHandler.setRoleHierarchy(context.getBean(roleHiearchyBeanNames[0], RoleHierarchy.class));
				}
				String[] grantedAuthorityDefaultsBeanNames = context.getBeanNamesForType(GrantedAuthorityDefaults.class);
				if (grantedAuthorityDefaultsBeanNames.length == 1) {
					GrantedAuthorityDefaults grantedAuthorityDefaults = context.getBean(grantedAuthorityDefaultsBeanNames[0], GrantedAuthorityDefaults.class);
					defaultHandler.setDefaultRolePrefix(grantedAuthorityDefaults.getRolePrefix());
				}
				String[] permissionEvaluatorBeanNames = context.getBeanNamesForType(PermissionEvaluator.class);
				if (permissionEvaluatorBeanNames.length == 1) {
					PermissionEvaluator permissionEvaluator = context.getBean(permissionEvaluatorBeanNames[0], PermissionEvaluator.class);
					defaultHandler.setPermissionEvaluator(permissionEvaluator);
				}
			}

			expressionHandler = postProcess(defaultHandler);
		}

		return expressionHandler;
	}
}
