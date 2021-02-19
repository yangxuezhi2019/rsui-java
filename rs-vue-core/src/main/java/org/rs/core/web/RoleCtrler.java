package org.rs.core.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsRole;
import org.rs.core.beans.RsRoleUser;
import org.rs.core.beans.model.RsRoleModel;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsRoleService;
import org.rs.core.service.RsUserService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("角色操作API")
@RestController
@RequestMapping("/role")
public class RoleCtrler {
	@Autowired
	private UserCtrler userCtrler;
	@Autowired
	private DeptCtrler deptCtrler;
	@Autowired
	private RsRoleService roleService;
	@Autowired
	private RsUserService userService;
	
	@ApiInfo("查询角色")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		String yhbh = MapUtils.getString(param, "yhbh","");
		String noyhbh = MapUtils.getString(param, "noyhbh","");
		DbPage page = CoreUtils.constructPage(param);
		
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("orderCause", "ORDER BY JSXH");
		if( !StringUtils.isEmpty(yhbh) ) {
			queryParam.put("yhbh", yhbh);
		}
		
		if( !StringUtils.isEmpty(noyhbh) ) {
			queryParam.put("noyhbh", noyhbh);
		}
		
		Map<String,Object> result = RespUtils.writeOk();
		List<RsRoleModel> list = roleService.queryBeans(queryParam, page);
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@ApiInfo("添加角色")
	@RequestMapping(path="add",method = RequestMethod.POST)
	public Map<String,Object> add( @RequestBody RsRole bean ){
		
		String jsnm = bean.getJsnm();
		if( jsnm == null || !jsnm.startsWith("ROLE_") ) {
			throw new RuntimeException("角色编号必须以ROLE_开头");
		}
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		bean.setJszt(0);
		roleService.insertBean(bean);
		return result;
	} 
	
	@ApiInfo("更新角色")
	@RequestMapping(path="save",method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody RsRole bean ){
		
		String jsnm = bean.getJsnm();
		if( jsnm == null || !jsnm.startsWith("ROLE_") ) {
			throw new RuntimeException("角色编号必须以ROLE_开头");
		}
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();		
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		roleService.updateBean(bean);
		return result;
	}
	
	@ApiInfo("删除角色")
	@RequestMapping(path="delete",method = RequestMethod.POST)
	public Map<String,Object> delete( @RequestBody List<String> ids ){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			roleService.deleteBean(id);
		}
		return result;
	}
	
	@ApiInfo("查询用户信息")
	@RequestMapping(path="user",method = RequestMethod.POST)
	public Map<String,Object> queryUser(@RequestBody Map<String,Object> param){
		
		return userCtrler.query(param);
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="user/add",method = RequestMethod.POST)
	public Map<String,Object> addRoleUser( @RequestBody Map<String,Object> param ){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		
		String jsbh = MapUtils.getString(param, "jsbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		
		List<RsRoleUser> roleUsers = new ArrayList<>();
		for( String yhbh:ids) {
			
			RsRoleUser bean = new RsRoleUser();
			bean.setYhbh(yhbh);
			bean.setJsbh(jsbh);
			bean.setCjrbh(userInfo.getYhbh());
			bean.setCjrmc(userInfo.getYhmc());
			bean.setCjsj(new Date());
			roleUsers.add(bean);
		}
		userService.addRoles(roleUsers);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="user/delete",method = RequestMethod.POST)
	public Map<String,Object> deleteRoleUser( @RequestBody Map<String,Object> param ){
		
		String jsbh = MapUtils.getString(param, "jsbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		userService.delRoleUsers(jsbh,ids);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
	
	@ApiInfo("查询机构树信息")
	@RequestMapping(path="dept/tree", method = RequestMethod.POST)
	public Map<String,Object> queryDeptTree(@RequestBody Map<String,Object> param){
		
		return deptCtrler.queryDeptTree(param);
	}
}
