package org.rs.core.mapper;

import org.rs.core.beans.model.RsUserAllModel;
import org.rs.core.beans.model.RsUserModel;
import org.rs.core.page.IPage;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface RsUserModelMapper {
	
	List<RsUserModel> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsUserAllModel queryBeanHasDeptsByYhbh(@Param("yhbh") String yhbh);
	
	int updateUserDept(@Param("yhbh") String yhbh, @Param("bmbh") String bmbh);
}
