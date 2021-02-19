package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsAccessLog;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsAccessLogMapper {

	List<RsAccessLog> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsAccessLog queryBeanById(@Param("logid") String logid);
	RsAccessLog queryBeanForUpdateById(@Param("logid") String logid);
	int insertBean(RsAccessLog rsAccessLog);
	int updateBean(RsAccessLog rsAccessLog);
	int updateChkBean(RsAccessLog rsAccessLog);
	int deleteBeanById(@Param("logid") String logid);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
