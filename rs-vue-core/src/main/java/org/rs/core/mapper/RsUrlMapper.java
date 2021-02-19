package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsUrl;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsUrlMapper {

	List<RsUrl> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsUrl queryBeanById(@Param("url") String url);
	int insertBean(RsUrl rsUrl);
	int updateBean(RsUrl rsUrl);
	int deleteInvalidUrl();
	int invalidUrl();
	int validUrl();
}
