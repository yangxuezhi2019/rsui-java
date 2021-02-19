package org.rs.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.rs.core.beans.RsStrategy;
import org.rs.core.beans.RsStrategyDesc;
import org.rs.core.beans.RsStrategyInfo;
import org.rs.core.beans.model.RsStrategyModel;
import org.rs.core.mapper.RsStrategyMapper;
import org.rs.core.service.RsStrategyService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RsStrategyServiceImpl implements RsStrategyService {

	@Autowired
	private RsStrategyMapper strategyMapper;
	private Map<String,Object> currentStrategyInfoMap;
	private Object currentStrategyInfoSync = new Object();

	@Override
	public int insertStrategyDesc( RsStrategyDesc bean ) {

		return strategyMapper.insertStrategyDesc(bean);
	}
	
	@Override
	public int insertStrategyInfo( RsStrategyInfo bean ) {

		return strategyMapper.insertStrategyInfo(bean);
	}
	
	@Override
	public int insertStrategy( RsStrategy bean ) {

		return strategyMapper.insertStrategy(bean);
	}
	
	@Override
	public List<RsStrategyModel> queryStrategy(){
		
		return strategyMapper.queryStrategyList();
	}
	 
	@Override
	public RsStrategyModel queryStrategyByClbh(String clbh) {
		return strategyMapper.queryStrategyByClbh(clbh);
	}
	
	@Override
	public int updateStrategyInfo(RsStrategyInfo bean) {
		
		RsStrategyInfo dbBean = strategyMapper.queryStrategyInfoById(bean.getClbh(), bean.getMsbh());
		if( dbBean != null ) {
			dbBean.setTmz(bean.getTmz());
			//dbBean.setTmzt(bean.getTmxh());
			strategyMapper.updateStrategyInfo(dbBean);
			synchronized (currentStrategyInfoSync) {
				currentStrategyInfoMap = null;
			}
		}
		return 0;
	}
	
	@Override
	public int updateStrategyZt(String clbh, Integer clzt) {
		
		int result = 0;
		if( clzt.equals(0)) {
			//只能启用一个安全策略
			strategyMapper.invalidStrategy();
		}
		result = strategyMapper.updateStrategyZt(clbh, clzt);
		synchronized (currentStrategyInfoSync) {
			currentStrategyInfoMap = null;
		}
		return result;
	}
	
	@Override
	public Map<String,Object> queryCurrentStrategyInfo(){
		
		Map<String,Object> result = null;
		synchronized (currentStrategyInfoSync) {
			
			if( currentStrategyInfoMap == null ) {
				
				currentStrategyInfoMap = new HashMap<>();
				List<RsStrategyInfo> list = strategyMapper.queryCurrentStrategyInfo();
				for( RsStrategyInfo item: list ) {
					
					currentStrategyInfoMap.put(item.getMsbh(), item.getTmz());
				}
			}
			result = currentStrategyInfoMap;
		}
		return result;
	}
	
	@Override
	public Integer getStrategyIntValue(String msbh) {
		
		Map<String,Object> map = queryCurrentStrategyInfo();
		return MapUtils.getInteger(map, msbh,null);
	}
	@Override
	public String getStrategyStringValue(String msbh) {
		Map<String,Object> map = queryCurrentStrategyInfo();
		return MapUtils.getString(map, msbh,null);
	}
	@Override
	public Boolean getStrategyBooleanValue(String msbh) {
		Map<String,Object> map = queryCurrentStrategyInfo();
		return MapUtils.getString(map, msbh,"0").equals("1");
	}
}
