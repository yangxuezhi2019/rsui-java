package org.rs.core.service;

import java.util.List;
import java.util.Map;

import org.rs.core.beans.RsStrategy;
import org.rs.core.beans.RsStrategyDesc;
import org.rs.core.beans.RsStrategyInfo;
import org.rs.core.beans.model.RsStrategyModel;

public interface RsStrategyService {

	int insertStrategyDesc( RsStrategyDesc bean );
	int insertStrategyInfo( RsStrategyInfo bean );
	int insertStrategy( RsStrategy bean );
	int updateStrategyZt(String clbh, Integer clzt);
	
	List<RsStrategyModel> queryStrategy();
	RsStrategyModel queryStrategyByClbh(String clbh);
	int updateStrategyInfo(RsStrategyInfo bean);
	
	Map<String,Object> queryCurrentStrategyInfo();
	
	Integer getStrategyIntValue(String msbh);
	String getStrategyStringValue(String msbh);
	Boolean getStrategyBooleanValue(String msbh);
}
