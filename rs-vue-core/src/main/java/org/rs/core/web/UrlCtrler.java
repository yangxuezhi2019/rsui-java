package org.rs.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsUrl;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.service.RsUrlService;
import org.rs.core.utils.CoreUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlCtrler {

	@Autowired
	private RsUrlService urlService;
	
	@ApiInfo("查询URL信息")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		String mc = MapUtils.getString(param, "mc","");
		String cdbh = MapUtils.getString(param, "cdbh","");
		String nocdbh = MapUtils.getString(param, "nocdbh","");
		
		Map<String,Object> queryParam = new HashMap<>();
		if( !StringUtils.isEmpty(mc) ) {
			queryParam.put("mc", StringTools.formatDbLikeValue(mc));
		}
		if( !StringUtils.isEmpty(cdbh) ) {
			queryParam.put("cdbh", cdbh);
		}
		if( !StringUtils.isEmpty(nocdbh) ) {
			queryParam.put("nocdbh", nocdbh);
		}
		List<RsUrl> list = urlService.queryBeans(queryParam, page);
		param.put("orderCause", "ORDER BY URL");
		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("urls", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@RequestMapping(path="/save",method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody Map<String,Object> urls ){
		
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
}
