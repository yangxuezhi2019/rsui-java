package org.rs.core.mapper;

import java.util.List;
import java.util.Map;

import org.rs.core.beans.RsMenu;
import org.rs.core.beans.RsRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsRoleMenuMapper {

	List<RsRoleMenu> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsRoleMenu queryBeanById(@Param("jsbh") String jsbh,@Param("cdbh") String cdbh);
	int insertBean(RsRoleMenu rsRoleMenu);
	int deleteBeanById(@Param("jsbh") String jsbh,@Param("cdbh") String cdbh);	
	List<RsMenu> queryMenusByJsbh(@Param("jsbh") List<String> jsbh);
	
	int deleteRoleMenuByJsbh(@Param("jsbh")String jsbh);
}
