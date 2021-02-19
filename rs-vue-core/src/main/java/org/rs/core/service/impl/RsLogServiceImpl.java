package org.rs.core.service.impl;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsAccessLog;
import org.rs.core.beans.RsLoginLog;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsAccessLogMapper;
import org.rs.core.mapper.RsLoginLogMapper;
import org.rs.core.service.RsLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.rs.core.page.IPage;

@Service
@Transactional(propagation= Propagation.SUPPORTS)
public class RsLogServiceImpl implements RsLogService {

	@Autowired
	private RsAccessLogMapper accessLogMapper;
	@Autowired
	private RsLoginLogMapper loginLogMapper;
	@Autowired
	private RsKeyGenerator key;

	@Override
	public int insertAccessLogBean( RsAccessLog bean ) {
		bean.setLogid("A"+key.getZipKey(ACCESS_LOG_KEY_NAME, 9));
		return accessLogMapper.insertBean( bean );
	}

	@Override
	public List<RsAccessLog> queryAccessLogList( Map<String,Object> param, IPage paramPage ) {

		 return accessLogMapper.queryBeanList( param, paramPage );
	}
	
	@Override
	public int insertLoginLogBean( RsLoginLog bean ) {
		
		bean.setLogid("L"+key.getZipKey(LOGIN_LOG_KEY_NAME, 9));
		return loginLogMapper.insertBean(bean);
	}
	
	@Override
	public List<RsLoginLog> queryLoginLogList( Map<String,Object> param, IPage paramPage ){
		
		return loginLogMapper.queryBeanList( param, paramPage );
	}

}
