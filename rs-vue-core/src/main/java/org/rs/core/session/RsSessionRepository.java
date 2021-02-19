package org.rs.core.session;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

public interface RsSessionRepository<S extends Session> extends FindByIndexNameSessionRepository<S>{

	void cleanUpExpiredSessions();
}
