package org.rs.core.mapper;

import java.util.List;
import org.rs.core.beans.RsStrategy;
import org.rs.core.beans.RsStrategyDesc;
import org.rs.core.beans.RsStrategyInfo;
import org.rs.core.beans.model.RsStrategyModel;
import org.apache.ibatis.annotations.Param;

public interface RsStrategyMapper {

	int insertStrategyDesc( RsStrategyDesc bean );
	int deleteStrategyDesc( @Param("msbh") String msbh );
	
	RsStrategyInfo queryStrategyInfoById(@Param("clbh") String clbh,@Param("msbh") String msbh);
	int insertStrategyInfo( RsStrategyInfo bean );
	int updateStrategyInfo( RsStrategyInfo bean );
	int deleteStrategyInfo( @Param("tmbh") String tmbh );
	
	int updateStrategyZt(@Param("clbh") String clbh,@Param("clzt") Integer clzt);
	int insertStrategy( RsStrategy bean );
	int deleteStrategy( @Param("clbh") String clbh );
	
	List<RsStrategyModel> queryStrategyList();
	RsStrategyModel queryStrategyByClbh(@Param("clbh")String clbh);
	
	List<RsStrategyInfo> queryCurrentStrategyInfo();
	int invalidStrategy();
}
