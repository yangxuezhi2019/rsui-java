package org.rs.core.security.access;

import java.util.Collection;
import java.util.List;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.FilterInvocation;

public class RsAccessDecisionManager implements AccessDecisionManager{

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private SecurityExpressionHandler<FilterInvocation> expressionHandler;
	
	public RsAccessDecisionManager(SecurityExpressionHandler<FilterInvocation> expressionHandler) {
		
		this.expressionHandler = expressionHandler;
	}
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		int grant = 0;
		FilterInvocation fi = (FilterInvocation) object;
		EvaluationContext ctx = expressionHandler.createEvaluationContext(authentication,fi);
		List<ConfigAttribute> attrList = (List<ConfigAttribute>) attributes;
		
		for( int i = 0; i < attrList.size()-1; i ++ ) {
			
			ConfigAttribute attribute = attrList.get(i);
			if (attribute instanceof RsConfigAttribute) {
				Expression expression = ((RsConfigAttribute) attribute).getAuthorizeExpression();
				if( ExpressionUtils.evaluateAsBoolean(expression, ctx) ) {
					grant ++;
				}
			}
		}
		
		if( grant == 0 ) {
			
			RsConfigAttribute defaultAttr = (RsConfigAttribute) attrList.get(attrList.size()-1);
			boolean defaultGrant = ExpressionUtils.evaluateAsBoolean(defaultAttr.getAuthorizeExpression(), ctx);
			if( !defaultGrant ) {
				throw new AccessDeniedException(messages.getMessage(
						"AbstractAccessDecisionManager.accessDenied", "Access is denied"));
			}
		}
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		
		return attribute instanceof RsConfigAttribute;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
