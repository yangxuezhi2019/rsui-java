package org.rs.core.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsAuthService;
import org.rs.core.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RsAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private static final Logger logger = LoggerFactory.getLogger(RsAuthenticationSuccessHandler.class);
	@Autowired
	private ObjectMapper jsonMapper;
	@Autowired
	private RsAuthService authService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		if( logger.isDebugEnabled() ) {
			logger.debug("auth ok");
		}
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		
		List<String> roles = userInfo.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
		List<String> jsbhList = userInfo.getAuthorities().stream().map(item->item.getJsbh()).collect(Collectors.toList());
		result.put("dlmc",userInfo.getUsername());
		result.put("yhmc",userInfo.getYhmc());
		result.put("roles",roles);
		if( roles.size() > 0 ) {
			result.put("menus",authService.queryMenuTreeByJsbh(jsbhList));
		}else {
			result.put("menus", new ArrayList<>());
		}		
		String resultStr = jsonMapper.writeValueAsString(result);
		response.getWriter().print(resultStr);
		response.flushBuffer();
	}
}
