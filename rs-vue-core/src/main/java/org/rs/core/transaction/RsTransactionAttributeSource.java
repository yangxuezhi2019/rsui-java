package org.rs.core.transaction;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.util.ClassUtils;

public class RsTransactionAttributeSource implements TransactionAttributeSource, Serializable{

	private static final long serialVersionUID = 1L;
	private Pattern pattern;
	private Map<String,RuleBasedTransactionAttribute> map = new HashMap<>();
	public RsTransactionAttributeSource( String patternStr ) {
		
		pattern = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
	}

	@Override
	public TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
		
		if (!ClassUtils.isUserLevelMethod(method)) {
			return null;
		}
		
		String methodName = method.getName();
		RuleBasedTransactionAttribute transactionAttribute = map.get(methodName);
		if( transactionAttribute == null ) {
			synchronized (map) {
				transactionAttribute = map.get(methodName);
				if( transactionAttribute == null ) {
					Matcher matcher = pattern.matcher(methodName);
					if( matcher.matches() ) {
						
						transactionAttribute = new RuleBasedTransactionAttribute();
						transactionAttribute.setTimeout(-1);
						transactionAttribute.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
					}else {
						transactionAttribute = new RuleBasedTransactionAttribute();
						transactionAttribute.setTimeout(-1);
						transactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
					}
					map.put(methodName, transactionAttribute);
				}
			}
		}
		return transactionAttribute;
	}
}
