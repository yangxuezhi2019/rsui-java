package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsLoginLog;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsLoginLogMapper {

	List<RsLoginLog> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsLoginLog queryBeanById(@Param("logid") String logid);
	RsLoginLog queryBeanForUpdateById(@Param("logid") String logid);
	int insertBean(RsLoginLog rsLoginLog);
	int updateBean(RsLoginLog rsLoginLog);
	int updateChkBean(RsLoginLog rsLoginLog);
	int deleteBeanById(@Param("logid") String logid);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
