package org.rs.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.rs.core.beans.RsRoleUrl;

public interface RsRoleUrlMapper {

	int insertBean(RsRoleUrl rsRoleUrl);
	int deleteBean(RsRoleUrl rsRoleUrl);
	int deleteRoleUrlByUrl(@Param("url") String url);
	int deleteRoleUrlByJsbh(@Param("jsbh")String jsbh);
}
