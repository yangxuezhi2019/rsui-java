package org.rs.core.transaction;

import java.lang.reflect.Method;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
public class RsAspectJExpressionPointcut extends AspectJExpressionPointcut {
	
	@Override
	public boolean matches(Method method, Class<?> targetClass, boolean hasIntroductions) {
		
		if(AnnotatedElementUtils.hasAnnotation(method, Transactional.class) 
				|| AnnotatedElementUtils.hasAnnotation(targetClass, Transactional.class)) {
			return false;
		}
		return super.matches(method, targetClass,hasIntroductions);
	}
}
