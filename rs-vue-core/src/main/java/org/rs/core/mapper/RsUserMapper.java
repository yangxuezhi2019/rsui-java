package org.rs.core.mapper;

import org.rs.core.beans.RsUser;
import org.apache.ibatis.annotations.Param;

public interface RsUserMapper {

	//List<RsUser> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsUser queryBeanById(@Param("yhbh") String yhbh);
	RsUser queryBeanForUpdateById(@Param("yhbh") String yhbh);
	RsUser queryBeanByDlmc(@Param("dlmc") String dlmc);
	int insertBean(RsUser rsUser);
	int updateBean(RsUser rsUser);
	int updateChkBean(RsUser rsUser);
	int deleteBeanById(@Param("yhbh") String yhbh);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
