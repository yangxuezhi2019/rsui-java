package org.rs.core.service;

import java.util.List;

import org.rs.core.beans.RsSession;
import org.rs.core.beans.RsSessionAttribute;
import org.rs.core.beans.model.RsSessionModel;

public interface RsSessionService {

	int insertSessionAndAttributes(RsSession bean,List<RsSessionAttribute> attributes);
	int updateSessionAndAttributes(RsSession bean,
		List<RsSessionAttribute> insertAttributes,List<RsSessionAttribute> updateAttributes,List<RsSessionAttribute> deleteAttributes);
	int deleteSessionByExpiryTime(Long currentTimeMillis);
	int getSessionCount();
	int deleteBySessionId(String sessionId);
	RsSessionModel findBySessionId( String sessionId );
	List<RsSessionModel> findByPrincipalName(String principalName);
	int insertAttributes(List<RsSessionAttribute> attributes);
	int updateAttributes(List<RsSessionAttribute> attributes);
	int deleteAttributes(List<RsSessionAttribute> attributes);
}
