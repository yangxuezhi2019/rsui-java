package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsAccessLog;
import org.rs.core.beans.RsLoginLog;
import org.rs.core.page.IPage;

public interface RsLogService {

	static public final String ACCESS_LOG_KEY_NAME = "_RS_ACCESS_LOG_KEY_";
	static public final String LOGIN_LOG_KEY_NAME = "_RS_LOGIN_LOG_KEY_";
	int insertAccessLogBean( RsAccessLog bean );
	List<RsAccessLog> queryAccessLogList( Map<String,Object> param, IPage paramPage );
	
	int insertLoginLogBean( RsLoginLog bean );
	List<RsLoginLog> queryLoginLogList( Map<String,Object> param, IPage paramPage );
}
