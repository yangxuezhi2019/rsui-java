package org.rs.core.service;

import java.util.List;
import java.util.Map;

import org.rs.core.beans.RsMenu;
import org.rs.core.beans.RsRoleMenu;
import org.rs.core.beans.RsRoleUrl;

public interface RsAuthService {
	
	int insertRoleUrl(RsRoleUrl ...beans );
	int insertRoleUrl(List<RsRoleUrl> beans );
	int deleteRoleUrl(RsRoleUrl ...beans );
	int deleteRoleUrl(List<RsRoleUrl> beans );
	
	int updateRoleMenu(RsRoleMenu bean );
	int updateRoleMenus(List<RsRoleMenu> beans);
	int deleteRoleMenu(String jsbh, String cdbh);
	int deleteRoleMenu(String jsbh);
	
	//根据角色获取菜单信息
	List<Map<String,Object>> queryMenuTreeByJsbh(List<String> jsbhList);
	List<RsMenu> queryMenuByJsbh(List<String> jsbhList);
}
