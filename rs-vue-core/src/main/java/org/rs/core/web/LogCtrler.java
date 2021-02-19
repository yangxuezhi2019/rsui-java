package org.rs.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsAccessLog;
import org.rs.core.beans.RsLoginLog;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.service.RsLogService;
import org.rs.core.utils.CoreUtils;
import org.rs.utils.SystemInfoUtils;
import org.rs.utils.SystemInfoUtils.RsSystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("LOG信息API")
@RestController
@RequestMapping("/log")
public class LogCtrler {
	
	@Autowired
	private RsLogService logService;
	
	@ApiInfo(value="查询AccessLOG信息",log=false)
	@RequestMapping(path="access",method = RequestMethod.POST)
	public Map<String,Object> queryAccessLog(@RequestBody Map<String,Object> param){
		
		DbPage paramPage = CoreUtils.constructPage(param);
		
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("orderCause", "ORDER BY ACCESSDATE DESC");
		
		Map<String,Object> result = RespUtils.writeOk();
		List<RsAccessLog> list = logService.queryAccessLogList(queryParam, paramPage);
		result.put("list", list);
		CoreUtils.putPageInfo(paramPage, result);
		return result;
	}
	
	@ApiInfo(value="查询LoginLOG信息",log=false)
	@RequestMapping(path="login",method = RequestMethod.POST)
	public Map<String,Object> queryLoginLog(@RequestBody Map<String,Object> param){
		
		DbPage paramPage = CoreUtils.constructPage(param);
		
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("orderCause", "ORDER BY LOGINDATE DESC");
		
		Map<String,Object> result = RespUtils.writeOk();
		List<RsLoginLog> list = logService.queryLoginLogList(queryParam, paramPage);
		result.put("list", list);
		CoreUtils.putPageInfo(paramPage, result);
		return result;
	}
	
	@ApiInfo(value="查询SysLog信息",log=false)
	@RequestMapping(path="info",method = RequestMethod.POST)
	public Map<String,Object> querySysLog(@RequestBody Map<String,Object> param){
		
		RsSystemInfo systemInfo = SystemInfoUtils.getSystemInfo();
		Map<String,Object> result = RespUtils.writeOk();
		result.put("data", systemInfo);
		return result;
	}
}
