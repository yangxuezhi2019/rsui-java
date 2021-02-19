package org.rs.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.rs.core.beans.RsMenu;
import org.rs.core.beans.RsRoleMenu;
import org.rs.core.beans.RsRoleUrl;
import org.rs.core.event.RsSecurityEventPublisher;
import org.rs.core.mapper.RsMenuMapper;
import org.rs.core.mapper.RsRoleMenuMapper;
import org.rs.core.mapper.RsRoleUrlMapper;
import org.rs.core.service.RsAuthService;
import org.rs.core.utils.MenuTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RsAuthServiceImpl implements RsAuthService{
	
	@Autowired
	private RsRoleMenuMapper roleMenuMapper;
	@Autowired
	private RsMenuMapper menuMapper;
	@Autowired
	private RsRoleUrlMapper roleUrlMapper;
	@Autowired(required=false)
	private RsSecurityEventPublisher rsSecurityEventPublisher;

	@Override
	public int insertRoleUrl(RsRoleUrl ...beans ) {
		
		return insertRoleUrl(Arrays.asList(beans));
	}
	@Override
	public int insertRoleUrl(List<RsRoleUrl> beans) {

		int result = 0;
		for(RsRoleUrl bean : beans ) {
			result += roleUrlMapper.insertBean(bean);
		}
		//重新加载授权
		if( rsSecurityEventPublisher != null ) {
			rsSecurityEventPublisher.refreshMetadataSource();
		}
		return result;
	}
	
	@Override
	public int deleteRoleUrl(RsRoleUrl ...beans ) {
		return deleteRoleUrl(Arrays.asList(beans));
	}

	@Override
	public int deleteRoleUrl(List<RsRoleUrl> beans) {
		
		int result = 0;
		for(RsRoleUrl bean : beans ) {
			result += roleUrlMapper.deleteBean(bean);
		}
		//重新加载授权
		if( rsSecurityEventPublisher != null ) {
			rsSecurityEventPublisher.refreshMetadataSource();
		}
		return result;
	}

	@Override
	public int updateRoleMenu(RsRoleMenu bean) {
		
		RsRoleMenu old = roleMenuMapper.queryBeanById(bean.getJsbh(), bean.getCdbh());
		if( old == null ) {
			roleMenuMapper.insertBean(bean);
		}
		return 0;
	}
	
	@Override
	public int updateRoleMenus(List<RsRoleMenu> beans) {
		
		int result = 0;
		for(RsRoleMenu bean : beans ) {
			result += roleMenuMapper.insertBean(bean);
		}
		return result;
	}
	
	@Override
	public int deleteRoleMenu(String jsbh, String cdbh) {
		
		return roleMenuMapper.deleteBeanById(jsbh, cdbh);
	}
	
	@Override
	public int deleteRoleMenu(String jsbh ) {
		
		return roleMenuMapper.deleteRoleMenuByJsbh(jsbh);
	}
	
	@Override
	public List<RsMenu> queryMenuByJsbh(List<String> jsbhList){
		
		return roleMenuMapper.queryMenusByJsbh(jsbhList);
	}

	@Override
	public List<Map<String,Object>> queryMenuTreeByJsbh(List<String> jsbhList){
		
		List<Map<String,Object>> result = null;
		List<RsMenu> menus = roleMenuMapper.queryMenusByJsbh(jsbhList);
		try {
			List<Map<String,Object>> menuTreeList = MenuTools.constructMenuTree(menus, menuMapper);
			result = constructMenuModel(menuTreeList);
		}catch(Exception ex) {
			
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> constructMenuModel(List<Map<String,Object>> menuWrapList){
		
		List<Map<String,Object>> result = new ArrayList<>();
		for( Map<String,Object> wrap : menuWrapList) {
			
			Map<String,Object> model = new LinkedHashMap<>();
			model.put("id", MapUtils.getString(wrap, "cdbh"));
			model.put("path", MapUtils.getString(wrap, "cdlj"));
			model.put("icon", MapUtils.getString(wrap, "icon"));
			model.put("title", MapUtils.getString(wrap, "cdmc"));
			model.put("keepAlive", MapUtils.getInteger(wrap, "cdlx").intValue()==0?false:true);
			result.add(model);
			
			Object childMenus = MapUtils.getObject(wrap, "menus");
			List<Map<String,Object>> wrapChilds = null;
			if( childMenus != null ) {
				
				wrapChilds = (List<Map<String, Object>>) childMenus;
				if( wrapChilds.size() > 0 ) {
					List<Map<String,Object>> modelChilds = constructMenuModel(wrapChilds);
					model.put("menus",modelChilds);
				}
			}
		}
		return result;
	}
}
