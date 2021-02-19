package org.rs.core.service.impl;

import java.util.List;

import org.rs.core.beans.RsSession;
import org.rs.core.beans.RsSessionAttribute;
import org.rs.core.beans.model.RsSessionModel;
import org.rs.core.mapper.RsSessionMapper;
import org.rs.core.service.RsSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RsSessionServiceJdbcImpl implements RsSessionService{
	
	@Autowired
	private RsSessionMapper sessionMapper;
	
	@Override
	public int insertSessionAndAttributes(RsSession bean,List<RsSessionAttribute> attributes) {
		
		int result = sessionMapper.insertSession(bean);
		insertAttributes(attributes);
		return result;
	}

	@Override
	public int updateSessionAndAttributes(RsSession bean,
		List<RsSessionAttribute> insertAttributes,List<RsSessionAttribute> updateAttributes,List<RsSessionAttribute> deleteAttributes) {
		
		int result = sessionMapper.updateSession(bean);
		insertAttributes(insertAttributes);
		updateAttributes(updateAttributes);
		deleteAttributes(deleteAttributes);
		return result;
	}
	
	@Override
	public int deleteSessionByExpiryTime(Long currentTimeMillis) {
		
		return sessionMapper.deleteSessionByExpiryTime(currentTimeMillis);
	}

	@Override
	public int getSessionCount() {
		
		return sessionMapper.getSessionCount(System.currentTimeMillis());
	}

	@Override
	public int deleteBySessionId(String sessionId) {
		
		return sessionMapper.deleteBySessionId(sessionId);
	}

	@Override
	public RsSessionModel findBySessionId(String sessionId) {
		
		return sessionMapper.findBySessionId(sessionId);
	}

	@Override
	public List<RsSessionModel> findByPrincipalName(String principalName) {
		
		return sessionMapper.findByPrincipalName(principalName);
	}

	@Override
	public int insertAttributes(List<RsSessionAttribute> attributes) {
		int result = 0;
		for( RsSessionAttribute item : attributes ) {
			
			result += sessionMapper.insertAttribute(item);
		}
		return result;
	}

	@Override
	public int updateAttributes(List<RsSessionAttribute> attributes) {
		int result = 0;
		for( RsSessionAttribute item : attributes ) {
			
			result += sessionMapper.updateAttribute(item);
		}
		return result;
	}

	@Override
	public int deleteAttributes(List<RsSessionAttribute> attributes) {
		int result = 0;
		for( RsSessionAttribute item : attributes ) {
			
			result += sessionMapper.deleteAttribute(item);
		}
		return result;
	}
}
