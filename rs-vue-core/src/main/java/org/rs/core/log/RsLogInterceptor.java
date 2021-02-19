package org.rs.core.log;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsAccessLog;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsLogService;
import org.rs.core.utils.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class RsLogInterceptor implements MethodInterceptor, Serializable{
	
	private ObjectMapper jsonMapper;
	private RsLogService logService;
	public RsLogInterceptor(RsLogService logService,ObjectMapper jsonMapper) {
		
		this.logService = logService;
		this.jsonMapper = jsonMapper;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		String parameters = jsonMapper.writeValueAsString(invocation.getArguments());
		long startTime = System.currentTimeMillis();
		Object result = invocation.proceed();		
		//String className = invocation.getClass().getName();
		Method method = invocation.getMethod();
		
		String methodName = method.getDeclaringClass().getName() + "." + method.getName() + "()";
		ApiInfo apiInfo = method.getAnnotation(ApiInfo.class);
		recordAccessLog(apiInfo.value(),methodName,parameters,System.currentTimeMillis() - startTime);
		return result;
	}
	
	private void recordAccessLog( String operation,String methodName, String parameters, long milliseconds ) {
		
		RsLoginUser userInfo = null;
		String ip = "";
		Authentication authentication = SecurityUtils.getAuthentication();
		if( authentication != null ) {
			Object principal = authentication.getPrincipal();
			Object details = authentication.getDetails();
			if( details instanceof WebAuthenticationDetails ) {
				
				ip = ((WebAuthenticationDetails)details).getRemoteAddress();
			}
			if( principal instanceof RsLoginUser ) {
				
				userInfo = (RsLoginUser)principal;
			}else {
				return;
			}
		}
		
		RsAccessLog bean = new RsAccessLog();
		bean.setMethodName(methodName);
		bean.setParameters(parameters);
		bean.setOperation(operation);
		bean.setIp(ip);
		bean.setMilliseconds((int)milliseconds);
		bean.setUserId(userInfo.getYhbh());
		bean.setUserName(userInfo.getYhmc());
		bean.setAccessDate(new Date());
		bean.setDeptId(userInfo.getBmbh());
		bean.setDeptName(userInfo.getBmmc());
		bean.setDeptPath(userInfo.getBmlj());
		logService.insertAccessLogBean(bean);
	}
}
