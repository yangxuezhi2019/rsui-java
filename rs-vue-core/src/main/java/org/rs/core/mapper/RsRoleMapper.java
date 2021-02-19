package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsRole;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsRoleMapper {

	List<RsRole> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsRole queryBeanById(@Param("jsbh") String jsbh);
	RsRole queryBeanForUpdateById(@Param("jsbh") String jsbh);
	RsRole queryBeanByJsnm(@Param("jsnm") String jsnm);
	int insertBean(RsRole rsRole);
	int updateBean(RsRole rsRole);
	int updateChkBean(RsRole rsRole);
	int deleteBeanById(@Param("jsbh") String jsbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
