package org.rs.core.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.beans.RsDict;
import org.rs.core.beans.RsDictInfo;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsDictService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictCtrler {

	@Autowired
	private RsDictService dictService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		String zdmc = MapUtils.getString(param, "zdmc","");
		Map<String,Object> queryParam = new HashMap<>();
		
		if( !StringUtils.isEmpty(zdmc)) {
			
			String likeStr = StringTools.formatDbLikeValue(zdmc);
			queryParam.put("whereCause", "zdbh like " + likeStr + " OR zdmc like " + likeStr);
		}
		
		Map<String,Object> result = RespUtils.writeOk();		
		List<RsDict> list = dictService.queryBeans(queryParam, page);
		
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public Map<String,Object> add( @RequestBody RsDict bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		dictService.insertBean(bean);
		return result;
	}
	
	@RequestMapping(path="save",method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody RsDict bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();		
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		dictService.updateBean(bean);
		return result;
	}
	
	@RequestMapping(path="delete",method = RequestMethod.POST)
	public Map<String,Object> delete( @RequestBody List<String> ids ){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			dictService.deleteBean(id);
		}
		return result;
	}
	
	@RequestMapping(path="/info",method = RequestMethod.POST)
	public Map<String,Object> queryInfo(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		Map<String,Object> queryParam = new HashMap<>();
		
		String zdbh = MapUtils.getString(param, "zdbh","");
		if( !StringUtils.isEmpty(zdbh)) {
			
			queryParam.put("zdbh", zdbh);
		}
		
		queryParam.put("orderCause", "ORDER BY TMXH");
		
		Map<String,Object> result = RespUtils.writeOk();		
		List<RsDictInfo> list = dictService.queryInfoBeans(queryParam, page);
		
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@RequestMapping(path="/info/add",method = RequestMethod.POST)
	public Map<String,Object> addInfo( @RequestBody RsDictInfo bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		if(StringUtils.isEmpty(bean.getTmpbh())) {
			bean.setTmpbh("");
		}
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		dictService.insertInfoBean(bean);
		return result;
	}
	
	@RequestMapping(path="/info/save",method = RequestMethod.POST)
	public Map<String,Object> saveInfo( @RequestBody RsDictInfo bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();		
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		dictService.updateInfoBean(bean);
		return result;
	}
	
	@RequestMapping(path="/info/delete",method = RequestMethod.POST)
	public Map<String,Object> deleteInfo( @RequestBody List<String> ids ){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			dictService.deleteInfoBean(id);
		}
		return result;
	}
}
