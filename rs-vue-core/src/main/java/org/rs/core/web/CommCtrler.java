package org.rs.core.web;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.beans.RsDictInfo;
import org.rs.core.beans.RsUserPassword;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsDictService;
import org.rs.core.service.RsStrategyService;
import org.rs.core.service.RsUserService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.RsaUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comm")
public class CommCtrler {

	@Autowired
	private RsDictService dictService;
	@Autowired
	private RsUserService userService;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
	private RsStrategyService strategyService;
	
	@RequestMapping(path="dict",method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		String zdbh = MapUtils.getString(param, "zdbh","");
		Map<String,Object> queryParam = new HashMap<>();
		
		queryParam.put("zdbh", zdbh);
		queryParam.put("orderCause", "ORDER BY TMXH,TMZJ");
		
		Map<String,Object> result = RespUtils.writeOk();		
		List<RsDictInfo> list = dictService.queryInfoBeans(queryParam, null);
		
		result.put("dicts", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@RequestMapping(path="password",method = RequestMethod.POST)
	public Map<String,Object> changePassword(@RequestBody Map<String,Object> param){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		String oldpwd = MapUtils.getString(param, "oldpwd","");
		String newpwd = MapUtils.getString(param, "newpwd","");
		PrivateKey privateKey = RsaUtils.generateRSAPrivateKey(RsaUtils.privateKey);
		byte[] oldPwdBytes = RsaUtils.decrypt(privateKey, Base64.decodeBase64(oldpwd));
		byte[] newPpwdBytes = RsaUtils.decrypt(privateKey, Base64.decodeBase64(newpwd));
		
		Map<String,Object> result = RespUtils.writeOk();
		
		if( oldPwdBytes == null || newPpwdBytes == null ) {
			result.put("error_code", 1);
			result.put("error_desc", "解码失败！");
			return result;
		}
		
		oldpwd = StringUtils.newString(oldPwdBytes, "UTF-8");		
		newpwd = StringUtils.newString(newPpwdBytes, "UTF-8");		
		
		int passwordLevel = strategyService.getStrategyIntValue("PASSWORD_LEVEL");
		int passwordLength = strategyService.getStrategyIntValue("PASSWORD_MIN_LENGTH");
		if( newpwd.length() < passwordLength ) {
			result.put("error_code", 1);
			result.put("error_desc", "密码长度不能小于" + passwordLength);
			return result;
		}
		
		if( passwordLevel == 1 ) {
			
			if( !StringTools.hasDigitLetter(newpwd) || (!StringTools.hasUpperLetter(newpwd) && !StringTools.hasLowerLetter(newpwd)) ) {
				result.put("error_code", 1);
				result.put("error_desc", "密码必须包含字母和数字！");
				return result;
			}
		}else if(passwordLevel == 2){
			if( !StringTools.hasUpperLetter(newpwd) || !StringTools.hasLowerLetter(newpwd) || !StringTools.hasDigitLetter(newpwd)) {
				result.put("error_code", 1);
				result.put("error_desc", "密码必须包含大小写字母和数字！");
				return result;
			}
		}
		
		RsUserPassword userPassword = userService.queryUserPassword(userInfo.getYhbh());
		if( pwdEncoder.matches(oldpwd, userPassword.getDlmm()) ) {
			
			userPassword.setDlmm(newpwd);
			userService.updatePassword(userPassword);
		}else {
			result.put("error_code", 1);
			result.put("error_desc", "旧密码错误");
		}		
		return result;
	}
}
