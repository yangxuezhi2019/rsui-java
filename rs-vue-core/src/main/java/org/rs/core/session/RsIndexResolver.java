package org.rs.core.session;

import java.util.HashMap;
import java.util.Map;

import org.rs.core.security.authentication.RsLoginUser;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.session.IndexResolver;
import org.springframework.session.Session;

public class RsIndexResolver<S extends Session> implements IndexResolver<S>{

	private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	private static final Expression expression = new SpelExpressionParser().parseExpression("authentication?.principal");
	@Override
	public Map<String, String> resolveIndexesFor(S session) {
		
		Map<String,String> result = new HashMap<>();
		Object authentication = session.getAttribute(SPRING_SECURITY_CONTEXT);
		if (authentication != null) {
			Object principal = expression.getValue(authentication, Object.class);
			if( principal != null && principal instanceof RsLoginUser) {
				
				RsLoginUser user = (RsLoginUser)principal;
				result.put("yhbh", user.getYhbh());
			}else {
				result.put("yhbh", "");
			}
		}else {
			result.put("yhbh", "");
		}
		return result;
	}
}
