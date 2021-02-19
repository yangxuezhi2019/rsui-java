package org.rs.core.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsRoleUser;
import org.rs.core.beans.RsUser;
import org.rs.core.beans.RsUserPassword;
import org.rs.core.beans.model.RsUserModel;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsUserService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("用户信息API")
@RestController
@RequestMapping("/user")
public class UserCtrler {

	@Autowired
	private RoleCtrler roleCtrler;
	@Autowired
	private DeptCtrler deptCtrler;
	@Autowired
	private RsUserService userService;
	
	@ApiInfo(value="查询用户信息",log=true)
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		//long start = System.currentTimeMillis();
		DbPage page = CoreUtils.constructPage(param);
		String yhmc = MapUtils.getString(param, "yhmc","");
		String bmbh = MapUtils.getString(param, "bmbh","");
		String inbmbh = MapUtils.getString(param, "inbmbh","");
		String nobmbh = MapUtils.getString(param, "nobmbh","");
		String injsbh = MapUtils.getString(param, "injsbh","");
		String nojsbh = MapUtils.getString(param, "nojsbh","");
		
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("orderCause", "ORDER BY A.CJSJ");
		if( !StringUtils.isEmpty(bmbh) ) {
			queryParam.put("bmbh", bmbh);
			//whereCause += ""
		}
		if( !StringUtils.isEmpty(inbmbh) ) {
			queryParam.put("inbmbh", inbmbh);
			//whereCause += ""
		}
		
		if( !StringUtils.isEmpty(nobmbh) ) {
			queryParam.put("nobmbh", nobmbh);
		}
		
		if( !StringUtils.isEmpty(injsbh) ) {
			queryParam.put("injsbh", injsbh);
		}
		
		if( !StringUtils.isEmpty(nojsbh) ) {
			queryParam.put("nojsbh", nojsbh);
		}
		
		if( !StringUtils.isEmpty(yhmc) ) {
			queryParam.put("likeyhmc", StringTools.formatDbLikeValue(yhmc));
		}
		
		if( !userInfo.hasRole("ROLE_SYSADMIN") && StringUtils.isEmpty(inbmbh) ) {
			queryParam.put("whereCause", "B.BMLJ LIKE " + StringTools.formatDbLeftLikeValue(userInfo.getBmlj()));
		}
		
		Map<String,Object> result = RespUtils.writeOk();
		List<RsUserModel> list = userService.queryBeans(queryParam, page);
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		//System.out.println(System.currentTimeMillis()-start);
		return result;
	}	
	
	@ApiInfo("添加用户")
	@RequestMapping(path="add",method = RequestMethod.POST)
	public Map<String,Object> add( @RequestBody RsUser bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		bean.setSffh(0);
		bean.setYhzt(0);
		bean.setCqjc(0);
		bean.setDlcs(0);
		bean.setYhlx(1);
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		userService.insertBean(bean);
		return result;
	}
	
	@ApiInfo("更新用户")
	@RequestMapping(path="save",method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody RsUser bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		userService.updateBean(bean);
		return result;
	}
	
	@ApiInfo("删除用户")
	@RequestMapping(path="delete",method = RequestMethod.POST)
	public Map<String,Object> delete( @RequestBody List<String> ids ){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			userService.deleteBean(id);
		}
		return result;
	}
	
	@ApiInfo("修改用户密码")
	@RequestMapping(path="password",method = RequestMethod.POST)
	public Map<String,Object> changePassword( @RequestBody Map<String,Object> param ){
		
		Map<String,Object> result = RespUtils.writeOk();
		String yhbh = MapUtils.getString(param, "yhbh", "");
		RsUserPassword password = new RsUserPassword();
		password.setYhbh(yhbh);
		password.setDlmm("123456");
		userService.updatePassword(password);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ApiInfo("给用户添加角色")
	@RequestMapping(path="role/add",method = RequestMethod.POST)
	public Map<String,Object> addRole( @RequestBody Map<String,Object> param ){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		
		String yhbh = MapUtils.getString(param, "yhbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		
		List<RsRoleUser> roleUsers = new ArrayList<>();
		for( String jsbh:ids) {
			
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
	@ApiInfo("删除用户角色")
	@RequestMapping(path="role/delete",method = RequestMethod.POST)
	public Map<String,Object> deleteRole( @RequestBody Map<String,Object> param ){
		
		String yhbh = MapUtils.getString(param, "yhbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		userService.delUserRoles(yhbh,ids);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
	
	@ApiInfo("查询机构树信息")
	@RequestMapping(path="dept/tree", method = RequestMethod.POST)
	public Map<String,Object> queryDeptTree(@RequestBody Map<String,Object> param){
		
		return deptCtrler.queryDeptTree(param);
	}
	
	@ApiInfo("查询机构树信息")
	@RequestMapping(path="role", method = RequestMethod.POST)
	public Map<String,Object> queryRole(@RequestBody Map<String,Object> param){
		
		return roleCtrler.query(param);
	}
}
