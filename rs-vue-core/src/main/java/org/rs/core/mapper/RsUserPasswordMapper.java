package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsUserPassword;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsUserPasswordMapper {

	List<RsUserPassword> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsUserPassword queryBeanById(@Param("yhbh") String yhbh);
	RsUserPassword queryBeanForUpdateById(@Param("yhbh") String yhbh);
	int insertBean(RsUserPassword rsUserPassword);
	int updateBean(RsUserPassword rsUserPassword);
	int updateChkBean(RsUserPassword rsUserPassword);
	int deleteBeanById(@Param("yhbh") String yhbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
