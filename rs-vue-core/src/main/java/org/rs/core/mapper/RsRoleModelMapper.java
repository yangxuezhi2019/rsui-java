package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.model.RsRoleModel;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsRoleModelMapper {

	List<RsRoleModel> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
}
