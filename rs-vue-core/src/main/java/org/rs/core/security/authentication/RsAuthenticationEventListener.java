package org.rs.core.security.authentication;

import java.util.Date;
import org.rs.core.beans.RsLoginLog;
import org.rs.core.service.RsLogService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class RsAuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent>{

	private final RsLogService logService;
	
	public RsAuthenticationEventListener(RsLogService logService) {
		
		this.logService = logService;
	}
	
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		
		if( event instanceof AuthenticationSuccessEvent ) {
			//System.out.println(event.getAuthentication());
			recordLoginLog(event.getAuthentication());
		}
	}
	
	/**
	 * Record User LoginInfo
	 * */
	private void recordLoginLog( Authentication authentication) {
		
		RsLoginUser userInfo = (RsLoginUser) authentication.getPrincipal();
		String ip = "";
		Object details = authentication.getDetails();
		if( details instanceof WebAuthenticationDetails ) {
			
			ip = ((WebAuthenticationDetails)details).getRemoteAddress();
		}
		RsLoginLog bean = new RsLoginLog();
		bean.setLoginName(userInfo.getUsername());
		bean.setUserName(userInfo.getYhmc());
		bean.setSqly(userInfo.getSqlx());
		bean.setUserId(userInfo.getYhbh());
		bean.setIp(ip);
		bean.setLoginDate(new Date());
		bean.setDeptId(userInfo.getBmbh());
		bean.setDeptName(userInfo.getBmmc());
		bean.setDeptPath(userInfo.getBmlj());
		logService.insertLoginLogBean(bean);
	}

}
