package org.rs.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.rs.core.beans.RsSession;
import org.rs.core.beans.RsSessionAttribute;
import org.rs.core.beans.model.RsSessionModel;

public interface RsSessionMapper {

	int insertSession(RsSession bean);
	int updateSession(RsSession bean);
	int deleteSessionByExpiryTime(@Param("currentTimeMillis")Long currentTimeMillis);
	RsSessionModel findBySessionId(@Param("sessionId")String sessionId); 
	List<RsSessionModel> findByPrincipalName(@Param("principalName")String principalName);
	int getSessionCount(@Param("ticks")Long ticks);

	int deleteBySessionId(@Param("sessionId")String sessionId);
	
	int insertAttribute(RsSessionAttribute bean);
	int updateAttribute(RsSessionAttribute bean);
	int deleteAttribute(RsSessionAttribute bean);
}
