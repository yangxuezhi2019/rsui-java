package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDictInfo;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsDictInfoMapper {

	List<RsDictInfo> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsDictInfo queryBeanById(@Param("tmzj") String tmzj);
	RsDictInfo queryBeanForUpdateById(@Param("tmzj") String tmzj);
	RsDictInfo queryBeanByZdbhAndTmbh(@Param("zdbh") String zdbh,@Param("tmbh") String tmbh);
	int insertBean(RsDictInfo rsDictInfo);
	int updateBean(RsDictInfo rsDictInfo);
	int updateChkBean(RsDictInfo rsDictInfo);
	int deleteBeanById(@Param("tmzj") String tmzj);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
