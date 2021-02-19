package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.rs.core.beans.model.RsDeptModel;
import org.rs.core.page.IPage;

public interface RsDeptModelMapper {

	List<RsDeptModel> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
}
