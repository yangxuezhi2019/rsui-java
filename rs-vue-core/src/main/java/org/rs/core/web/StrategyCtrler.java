package org.rs.core.web;

import java.util.List;
import java.util.Map;

import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsStrategy;
import org.rs.core.beans.RsStrategyInfo;
import org.rs.core.beans.model.RsStrategyModel;
import org.rs.core.resp.RespUtils;
import org.rs.core.service.RsStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("策略操作API")
@RestController
@RequestMapping("/strategy")
public class StrategyCtrler {

	@Autowired
	private RsStrategyService strategyService;
	
	@ApiInfo("查询安全策略列表")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		Map<String,Object> result = RespUtils.writeOk();
		List<RsStrategyModel> list = strategyService.queryStrategy();
		result.put("list", list);
		return result;
	}
	
	@ApiInfo("保存安全策略信息")
	@RequestMapping(path="save",method = RequestMethod.POST)
	public Map<String,Object> save(@RequestBody RsStrategyInfo bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		strategyService.updateStrategyInfo(bean);
		return result;
	}
	
	@ApiInfo("修改安全策略状态")
	@RequestMapping(path="/zt/save",method = RequestMethod.POST)
	public Map<String,Object> saveZt(@RequestBody RsStrategy bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		strategyService.updateStrategyZt(bean.getClbh(),bean.getClzt());
		return result;
	}
}
