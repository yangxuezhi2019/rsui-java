package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDept;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsDeptMapper {

	List<RsDept> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsDept queryBeanById(@Param("bmbh") String bmbh);
	RsDept queryBeanForUpdateById(@Param("bmbh") String bmbh);
	RsDept queryBeanByBmdm(@Param("bmdm") String bmdm);
	int insertBean(RsDept rsDept);
	int updateBean(RsDept rsDept);
	int updateChkBean(RsDept rsDept);
	int deleteBeanById(@Param("bmbh") String bmbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
