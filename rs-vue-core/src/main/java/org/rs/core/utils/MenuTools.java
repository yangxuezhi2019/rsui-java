package org.rs.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.collections.MapUtils;
import org.rs.core.beans.RsMenu;
import org.rs.core.mapper.RsMenuMapper;
import org.rs.core.service.RsMenuService;

public class MenuTools {

	static public List<Map<String,Object>> constructMenuTree( List<RsMenu> menus, RsMenuMapper menuMapper ) throws Exception{
		
		List<Map<String,Object>> menuTreeList = new ArrayList<>();
		Map<String,Map<String,Object>> menuWrapMap = new HashMap<>();
		
		for( RsMenu menu : menus ) {
			
			Map<String,Object> wrap = new HashMap<>();
			BeanUtils.objectToMap(menu, wrap);
			addMenus(menus,wrap,menuTreeList,menuWrapMap,menuMapper);
		}
		
		return menuTreeList;
	}
	
	@SuppressWarnings("unchecked")
	static private void addMenus(
			List<RsMenu> menus, 
			Map<String,Object> menuWrap, 
			List<Map<String,Object>> root, 
			Map<String,Map<String,Object>> menuMap,
			RsMenuMapper menuMapper) throws Exception{
		
		String pcdbh = MapUtils.getString(menuWrap, "pcdbh", "");
		String cdbh = MapUtils.getString(menuWrap, "cdbh", "");
		if(RsMenuService.ROOT_KEY.equals(pcdbh)) {
			
			if( !menuMap.containsKey(cdbh) ){
				
				root.add(menuWrap);
				menuMap.put(cdbh, menuWrap);
			}
		}else {
			
			Map<String,Object> parentWrap = menuMap.get( pcdbh );
			List<Map<String,Object>> parentList = null;
			if( parentWrap == null ) {
				
				RsMenu parentMenu = loadRsMenu(pcdbh,menus,menuMapper);
				parentWrap = new HashMap<>();
				BeanUtils.objectToMap(parentMenu, parentWrap);
				parentList = new ArrayList<>();
				parentWrap.put("menus",parentList);
				addMenus(menus,parentWrap,root,menuMap,menuMapper);
			}else {
				
				Object menusObj = MapUtils.getObject(parentWrap, "menus");
				if( menusObj == null ) {
					parentList = new ArrayList<>();
					parentWrap.put("menus",parentList);
				}else {
					parentList = (List<Map<String, Object>>) menusObj;
				}
			}
			parentList.add(menuWrap);
			menuMap.put(cdbh, menuWrap);
		}		
	}
	
	static private RsMenu loadRsMenu( String cdbh , List<RsMenu> menus, RsMenuMapper menuMapper ) {
		
		RsMenu result = null;
		for( RsMenu menu : menus ) {
			
			if( menu.getCdbh().equals(cdbh) ) {
				result = menu;
				break;
			}
		}
		if( result == null ) {
			result = menuMapper.queryBeanById(cdbh);
		}
		return result;
	}
}
