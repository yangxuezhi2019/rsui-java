package org.rs.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;

public class RsConfigAttribute implements ConfigAttribute{

	private static final long serialVersionUID = 1L;
	private final Expression authorizeExpression;

	public RsConfigAttribute(Expression authorizeExpression) {
		this.authorizeExpression = authorizeExpression;
	}
	
	public Expression getAuthorizeExpression() {
		return this.authorizeExpression;
	}

	@Override
	public String getAttribute() {
		return null;
	}

	@Override
	public String toString() {
		return this.authorizeExpression.getExpressionString();
	}

	public static Collection<ConfigAttribute> createList(Expression ...expressions) {
		
		List<ConfigAttribute> result = new ArrayList<>();
		for( Expression item : expressions ) {
			result.add(new RsConfigAttribute(item));
		}
		return result;
	}
}
