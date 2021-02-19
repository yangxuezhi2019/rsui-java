package org.rs.core.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsMenu;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsMenuService;
import org.rs.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("菜单操作API")
@RestController
@RequestMapping("/menu")
public class MenuCtrler {
	
	@Autowired
	private RsMenuService menuService;

	@ApiInfo("查询菜单列表")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		param.put("orderCause", "ORDER BY PCDBH,CDXH,CDBH");
		
		List<Map<String,Object>> list = menuService.queryAllMenus();
		result.put("menus", list);
		return result;
	}
	
	@ApiInfo("根据菜单编号查询菜单")
	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public Map<String,Object> queryById(@PathVariable(value = "id") String cdbh){
		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("menu", menuService.queryBean(cdbh));
		return result;
	}
	
	@ApiInfo("添加菜单")
	@RequestMapping( path="add", method = RequestMethod.POST)
	public Map<String,Object> add( @RequestBody RsMenu menu){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		menu.setCdzt(0);
		RsLoginUser userInfo = SecurityUtils.getLoginUser();		
		menu.setCjrbh(userInfo.getYhbh());
		menu.setCjrmc(userInfo.getYhmc());
		menu.setCjsj(new Date());
		menuService.insertBean(menu);
		result.put("cdbh", menu.getCdbh());
		return result;
	}
	
	@ApiInfo("修改菜单")
	@RequestMapping( path="save", method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody RsMenu menu){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		menu.setCdzt(0);
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		menu.setCjrbh(userInfo.getYhbh());
		menu.setCjrmc(userInfo.getYhmc());
		menu.setCjsj(new Date());
		menuService.updateBean(menu);
		return result;
	}
	
	@ApiInfo("删除菜单")
	@RequestMapping( path="delete", method = RequestMethod.POST)
	public Map<String,Object> delete( @RequestBody List<String> ids){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			menuService.deleteBean(id);
		}
		return result;
	}
}
