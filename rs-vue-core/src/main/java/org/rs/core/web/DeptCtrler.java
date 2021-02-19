package org.rs.core.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsDept;
import org.rs.core.beans.RsDeptUser;
import org.rs.core.beans.model.RsDeptModel;
import org.rs.core.page.DbPage;
import org.rs.core.resp.RespUtils;
import org.rs.core.security.authentication.RsLoginUser;
import org.rs.core.service.RsDeptService;
import org.rs.core.service.RsUserService;
import org.rs.core.utils.CoreUtils;
import org.rs.core.utils.SecurityUtils;
import org.rs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ApiInfo("机构信息API")
@RestController
@RequestMapping("/dept")
public class DeptCtrler {

	@Autowired
	private UserCtrler userCtrler;
	@Autowired
	private RsUserService userService;
	@Autowired
	private RsDeptService deptService;
	
	@ApiInfo("查询机构信息")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String,Object> query(@RequestBody Map<String,Object> param){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		Map<String,Object> result = RespUtils.writeOk();
		
		param.put("orderCause", "ORDER BY A.BMPBH,A.BMXH,A.BMBH");
		DbPage page = CoreUtils.constructPage(param);
		
		List<RsDeptModel> list = null;
		if( !userInfo.hasRole("ROLE_SYSADMIN") ) {
			
			/*List<DeptInfo> depts = userInfo.getDepts();
			if(depts.size() == 0 ) {
				list = new ArrayList<>();
			}else{
				
				StringBuffer sb = new StringBuffer();
				DeptInfo item = depts.get(0);
				sb.append("( A.BMLJ LIKE " + CoreUtils.formatDbLeftLikeValue(item.getBmlj()));
				for(int i = 1; i < depts.size(); i ++ ) {
					item = depts.get(i);
					sb.append(" OR A.BMLJ LIKE " + CoreUtils.formatDbLeftLikeValue(item.getBmlj()));
				}
				sb.append(")");
				param.put("whereCause", sb.toString());
			}*/
			param.put("whereCause", "A.BMLJ LIKE " + StringTools.formatDbLeftLikeValue(userInfo.getBmlj()));
		}
		if( list == null ) {
			list = deptService.queryBeans(param, page);
		}
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@ApiInfo("查询机构树信息")
	@RequestMapping(path="tree", method = RequestMethod.POST)
	public Map<String,Object> queryDeptTree(@RequestBody Map<String,Object> param){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		Map<String,Object> result = RespUtils.writeOk();
		DbPage page = CoreUtils.constructPage(param);
		List<Map<String,Object>> list = null;
		boolean isSysAdmin = userInfo.hasRole("ROLE_SYSADMIN");
		List<String> bmljList = new ArrayList<>();
		if( !isSysAdmin ) {
			bmljList.add(userInfo.getBmlj());
			param.put("whereCause", "BMLJ LIKE " + StringTools.formatDbLeftLikeValue(userInfo.getBmlj()));
			/*List<DeptInfo> depts = userInfo.getFilterDepts();
			if(depts.size() == 0 ) {
				list = new ArrayList<>();
			}else{
				
				StringBuffer sb = new StringBuffer();
				DeptInfo item = depts.get(0);
				sb.append("( BMLJ LIKE " + CoreUtils.formatDbLeftLikeValue(item.getBmlj()));
				bmljList.add(item.getBmlj());
				for(int i = 1; i < depts.size(); i ++ ) {
					item = depts.get(i);
					sb.append(" OR BMLJ LIKE " + CoreUtils.formatDbLeftLikeValue(item.getBmlj()));
					bmljList.add(item.getBmlj());
				}
				sb.append(")");
				param.put("whereCause", sb.toString());
			}*/
		}
		if( list == null ) {
			list = deptService.queryDeptTree(param,bmljList,isSysAdmin);
		}
		result.put("list", list);
		CoreUtils.putPageInfo(page, result);
		return result;
	}
	
	@ApiInfo("添加机构")
	@RequestMapping( path="add", method = RequestMethod.POST)
	public Map<String,Object> add( @RequestBody RsDept bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		RsLoginUser userInfo = SecurityUtils.getLoginUser();		
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		bean.setBmzt(0);
		bean.setBmfh(0);
		deptService.insertBean(bean);
		result.put("bmbh", bean.getBmbh());
		return result;
	}
	
	@ApiInfo("修改机构")
	@RequestMapping( path="save", method = RequestMethod.POST)
	public Map<String,Object> save( @RequestBody RsDept bean ){
		
		Map<String,Object> result = RespUtils.writeOk();
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		bean.setCjrbh(userInfo.getYhbh());
		bean.setCjrmc(userInfo.getYhmc());
		bean.setCjsj(new Date());
		//bean.setBmzt(0);
		deptService.updateBean(bean);
		return result;
	}
	
	@ApiInfo("删除机构")
	@RequestMapping( path="delete", method = RequestMethod.POST)
	public Map<String,Object> delete( @RequestBody List<String> ids){
		
		Map<String,Object> result = RespUtils.writeOk();
		for( String id :ids ) {
			deptService.deleteBean(id);
		}
		return result;
	}
	
	@ApiInfo("查询用户信息")
	@RequestMapping(path="user",method = RequestMethod.POST)
	public Map<String,Object> queryUser(@RequestBody Map<String,Object> param){
		
		return userCtrler.query(param);
	}
	
	@SuppressWarnings("unchecked")
	@ApiInfo("修改用户所属的机构")
	@RequestMapping( path="user/edit", method = RequestMethod.POST)
	public Map<String,Object> editUsers( @RequestBody Map<String,Object> param ){
		
		String bmbh = MapUtils.getString(param, "bmbh","");
		List<String> yhbhList = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		userService.updateUsersBmbh(yhbhList, bmbh);
		Map<String,Object> result = RespUtils.writeOk();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ApiInfo("给机构添加关联用户")
	@RequestMapping( path="user/add", method = RequestMethod.POST)
	public Map<String,Object> addUsers( @RequestBody Map<String,Object> param ){
		
		RsLoginUser userInfo = SecurityUtils.getLoginUser();
		String bmbh = MapUtils.getString(param, "bmbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		List<RsDeptUser> beans = new ArrayList<>();
		for(String yhbh:ids) {
			RsDeptUser bean = new RsDeptUser();
			bean.setBmbh(bmbh);
			bean.setYhbh(yhbh);
			bean.setCjrbh(userInfo.getYhbh());
			bean.setCjrmc(userInfo.getYhmc());
			bean.setCjsj(new Date());
			beans.add(bean);
		}
		Map<String,Object> result = RespUtils.writeOk();
		deptService.addDeptUser(beans);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ApiInfo("从机构中删除关联用户")
	@RequestMapping( path="user/delete", method = RequestMethod.POST)
	public Map<String,Object> delUsers( @RequestBody Map<String,Object> param ){
		
		String bmbh = MapUtils.getString(param, "bmbh","");
		List<String> ids = (List<String>) MapUtils.getObject(param, "ids", new ArrayList<String>());
		Map<String,Object> result = RespUtils.writeOk();
		List<RsDeptUser> beans = new ArrayList<>();
		for(String yhbh:ids) {
			RsDeptUser bean = new RsDeptUser();
			bean.setBmbh(bmbh);
			bean.setYhbh(yhbh);
			beans.add(bean);
		}
		deptService.delDeptUser(beans);
		return result;
	}
}
