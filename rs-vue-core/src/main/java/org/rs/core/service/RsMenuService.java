package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsMenu;
import org.rs.core.page.IPage;

public interface RsMenuService {

	static public final String ROOT_KEY = "M000000000";
	static public final String MENU_KEY_NAME = "_RS_MENU_KEY_";
	
	String genMenuBh();
	int insertBean(RsMenu bean);
	int updateBean(RsMenu bean);
	int deleteBean(String cdbh);
	int deleteBeans(List<String> ids);
	
	RsMenu queryBean(String cdbh);
	List<RsMenu> queryBeans( Map<String,Object> param, IPage paramPage );
	
	/**
	 * 排队过后的查询，树形结构
	 * */
	List<Map<String,Object>> queryAllMenus();
}
