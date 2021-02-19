package org.rs.core.log;

import java.lang.reflect.Method;
import org.rs.core.annotation.ApiInfo;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class RsLogPointcut implements Pointcut{

	private final ClassFilter classFilter = new RsLogClassFilter();
	private final MethodMatcher methodMatcher = new RsLogMethodMatcher();
	@Override
	public ClassFilter getClassFilter() {
		
		return classFilter;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		
		return methodMatcher;
	}
	
	public static class RsLogClassFilter implements ClassFilter{

		@Override
		public boolean matches(Class<?> clazz) {
			
			return AnnotatedElementUtils.hasAnnotation(clazz, Controller.class) 
					|| AnnotatedElementUtils.hasAnnotation(clazz, RestController.class);
		}
		
	}
	
	public static class RsLogMethodMatcher extends StaticMethodMatcher{

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			
			ApiInfo apiInfo = AnnotatedElementUtils.findMergedAnnotation(method, ApiInfo.class);
			return apiInfo != null && apiInfo.log();
		}		
	}

}
