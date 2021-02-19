package org.rs.core.mapper;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDict;
import org.apache.ibatis.annotations.Param;
import org.rs.core.page.IPage;

public interface RsDictMapper {

	List<RsDict> queryBeanList(@Param("params") Map<String,Object> params, @Param("page") IPage paramPage);
	RsDict queryBeanById(@Param("zdzj") String zdzj);
	RsDict queryBeanForUpdateById(@Param("zdzj") String zdzj);
	RsDict queryBeanByZdbh(@Param("zdbh") String zdbh);
	int insertBean(RsDict rsDict);
	int updateBean(RsDict rsDict);
	int updateChkBean(RsDict rsDict);
	int deleteBeanById(@Param("zdzj") String zdzj);
	int deleteBeanByWhereCause(@Param("whereCause") String whereCause);
}
