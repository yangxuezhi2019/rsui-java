package org.rs.core.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {

	@Around(value="execution(* org.rs..service..*.*(..))")
	public Object aroundMethod(ProceedingJoinPoint jp) throws Throwable{
		Object result = jp.proceed();
		return result;
	}
	
//	@Before(value="execution(* org.rs..service..*.*(..))")
//	public void beforeMethod(JoinPoint jp) throws Throwable{
//		String methodName =jp.getSignature().getName();
//        System.out.println("【前置通知】the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
//	}
}
