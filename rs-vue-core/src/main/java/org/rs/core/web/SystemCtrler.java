package org.rs.core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsAuthService;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SystemCtrler {

	@Autowired
	private RsAuthService authService;
	
	@RequestMapping("/")
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
	@RequestMapping(path="/auth/info",method = RequestMethod.POST)
	public Map<String,Object> info(){
		
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
		return result;
	}
	
	@RequestMapping(path="/key",method = RequestMethod.GET)
	public Map<String,Object> key() {
		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("key", RsaUtils.publicKey);
		return result;
	}
}
