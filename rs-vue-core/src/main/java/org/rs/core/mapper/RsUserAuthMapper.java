package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsUserAuth;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsUserAuthMapper {

	List<RsUserAuth> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsUserAuth queryBeanById(@Param("sqbh") String sqbh);
	RsUserAuth queryBeanForUpdateById(@Param("sqbh") String sqbh);
	RsUserAuth queryBeanBySqlyAndDlmc(@Param("sqly") Integer sqly,@Param("dlmc") String dlmc);
	int insertBean(RsUserAuth rsUserAuth);
	int updateBean(RsUserAuth rsUserAuth);
	int updateChkBean(RsUserAuth rsUserAuth);
	int deleteBeanById(@Param("sqbh") String sqbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
