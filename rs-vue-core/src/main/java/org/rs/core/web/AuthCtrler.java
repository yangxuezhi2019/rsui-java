package org.rs.core.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsMenu;
import org.rs.core.beans.RsRoleMenu;
import org.rs.core.beans.RsRoleUrl;
import org.rs.core.beans.RsUrl;
import org.rs.core.beans.model.RsListModel;
import org.rs.core.beans.model.RsRoleModel;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsAuthService;
import org.rs.core.service.RsMenuService;
import org.rs.core.service.RsRoleService;
import org.rs.core.service.RsUrlService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("授权配置API")
@RestController
@RequestMapping("/auth")
public class AuthCtrler {
	
	@Autowired
	private RsAuthService authService;
	@Autowired
	private RsUrlService urlService;
	@Autowired
	private RsRoleService roleService;
	@Autowired
	private RsMenuService menuService;
	
	@ApiInfo("根据角色编号查询菜单列表")
	@RequestMapping(path="/menu",method = RequestMethod.POST)
	public Map<String,Object> queryMenu(@RequestBody Map<String,Object> param){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		String jsbh = MapUtils.getString(param,"jsbh", "");
		List<String> jsbhList = new ArrayList<>();
		jsbhList.add(jsbh);
		
		List<RsMenu> menus = authService.queryMenuByJsbh(jsbhList);
		result.put("menus", menus);
		return result;
	}
	
	@ApiInfo("查询角色列表")
	@RequestMapping(path="/menu/tree",method = RequestMethod.POST)
	public Map<String,Object> queryMenuTree(@RequestBody Map<String,Object> param){
				
		List<Map<String,Object>> list = menuService.queryAllMenus();		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("menus", list);
		return result;
	}
	
	@ApiInfo("添加菜单信息到角色")
	@RequestMapping(path="/menu/save",method = RequestMethod.POST)
	public Map<String,Object> menuSave(@RequestBody RsListModel model){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		Map<String,Object> result = RespUtils.writeOk();
		
		String jsbh = model.getBh();
		List<String> cdbhList = model.getIds();
		List<RsRoleMenu> beans = new ArrayList<>();
		
		for( String cdbh: cdbhList ) {
			
			if("root".equalsIgnoreCase(cdbh))
				continue;
			
			RsRoleMenu bean = new RsRoleMenu();
			bean.setCdbh(cdbh);
			bean.setJsbh(jsbh);
			bean.setCjrbh(userInfo.getYhbh());
			bean.setCjrmc(userInfo.getYhmc());
			bean.setCjsj(new Date());
			beans.add(bean);
		}
		authService.deleteRoleMenu(jsbh);
		authService.updateRoleMenus(beans);
		return result;
	}
	
	@RequestMapping(path="role",method = RequestMethod.POST)
	public Map<String,Object> queryRole(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("orderCause", "ORDER BY JSXH");
		List<RsRoleModel> list = roleService.queryBeans(queryParam, page);
		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@ApiInfo("查询URL信息")
	@RequestMapping(path="url",method = RequestMethod.POST)
	public Map<String,Object> queryUrl(@RequestBody Map<String,Object> param){
		
		DbPage page = CoreUtils.constructPage(param);
		String mc = MapUtils.getString(param, "mc","");
		String injsbh = MapUtils.getString(param, "injsbh","");
		String nojsbh = MapUtils.getString(param, "nojsbh","");
		
		Map<String,Object> queryParam = new HashMap<>();
		if( !StringUtils.isEmpty(mc) ) {
			queryParam.put("mc", StringTools.formatDbLikeValue(mc));
		}
		if( !StringUtils.isEmpty(injsbh) ) {
			queryParam.put("injsbh", injsbh);
		}
		if( !StringUtils.isEmpty(nojsbh) ) {
			queryParam.put("nojsbh", nojsbh);
		}
		List<RsUrl> list = urlService.queryBeans(queryParam, page);
		param.put("orderCause", "ORDER BY URL");
		
		Map<String,Object> result = RespUtils.writeOk();
		result.put("urls", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@ApiInfo("添加URL到角色")
	@RequestMapping(path="/url/add",method = RequestMethod.POST)
	public Map<String,Object> addUrl(@RequestBody RsListModel model){

		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		String jsbh = model.getBh();
		List<String> idList = model.getIds();
		List<RsRoleUrl> beans = new ArrayList<>();
		
		for( String id: idList ) {
			
			RsRoleUrl bean = new RsRoleUrl();
			bean.setJsbh(jsbh);
			bean.setUrl(id);
			bean.setCjrbh(userInfo.getYhbh());
			bean.setCjrmc(userInfo.getYhmc());
			bean.setCjsj(new Date());
			beans.add(bean);
		}
		authService.insertRoleUrl(beans);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
	
	@ApiInfo("从角色删除URL")
	@RequestMapping(path="/url/delete",method = RequestMethod.POST)
	public Map<String,Object> deleteUrl(@RequestBody RsListModel model){

		String jsbh = model.getBh();
		List<String> idList = model.getIds();
		List<RsRoleUrl> beans = new ArrayList<>();
		
		for( String id: idList ) {
			
			RsRoleUrl bean = new RsRoleUrl();
			bean.setJsbh(jsbh);
			bean.setUrl(id);
			beans.add(bean);
		}
		authService.deleteRoleUrl(beans);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
}
