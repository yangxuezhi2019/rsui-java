package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.model.RsUrlRolesModel;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsUrlModelMapper {

	List<RsUrlRolesModel> queryUrlRolesList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	
}
