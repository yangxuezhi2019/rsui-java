package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsMenu;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsMenuMapper {

	List<RsMenu> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsMenu queryBeanById(@Param("cdbh") String cdbh);
	RsMenu queryBeanForUpdateById(@Param("cdbh") String cdbh);
	int insertBean(RsMenu rsMenu);
	int updateBean(RsMenu rsMenu);
	int updateChkBean(RsMenu rsMenu);
	int deleteBeanById(@Param("cdbh") String cdbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
