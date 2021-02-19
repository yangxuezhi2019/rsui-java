package org.rs.core.security;

import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.rs.core.service.RsSessionService;
import org.rs.core.service.RsStrategyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.util.Assert;

public class RsSessionAuthenticationStrategy implements
		MessageSourceAware, SessionAuthenticationStrategy{
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final RsSessionService sessionService;
	private final FindByIndexNameSessionRepository<?> repository;
	private final RsStrategyService strategyService;
	private final SessionRegistry sessionRegistry;
	
	public RsSessionAuthenticationStrategy(ApplicationContext ctx,FindByIndexNameSessionRepository<?> repository,SessionRegistry sessionRegistry) {
		
		this.repository = repository;
		this.sessionService = ctx.getBean(RsSessionService.class);
		this.strategyService = ctx.getBean(RsStrategyService.class);
		this.sessionRegistry = sessionRegistry;
	}
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionAuthenticationException {
		
		//总并发数检测
		boolean sessionControl = strategyService.getStrategyBooleanValue("SESSION_CONTROL");
		if(!sessionControl)
			return;
		
		int maxSessionCount = strategyService.getStrategyIntValue("MAX_SESSION_COUNT");		
		if( maxSessionCount > 0 ) {
			// check system sessions
			//sessionRegistry.getAllSessions(principal, includeExpiredSessions);
			int systemSessionCount = sessionService.getSessionCount();
			if( systemSessionCount >= maxSessionCount ) {
				
				throw new SessionAuthenticationException(
						String.format("Maximum sessions of %d for system exceeded",maxSessionCount));
			}
		}
		
		int userSessionCount = strategyService.getStrategyIntValue("USER_SESSION");
		if( userSessionCount <=0 ) {
			// We permit unlimited logins
			return;
		}
		final List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication,true);
		if(sessions.size() < userSessionCount ) {
			// They haven't got too many login sessions running at present
			return;
		}
		
		if ( userSessionCount == sessions.size() ) {
			HttpSession session = request.getSession(false);

			if (session != null) {
				// Only permit it though if this request is associated with one of the
				// already registered sessions
				for (SessionInformation si : sessions) {
					if (si.getSessionId().equals(session.getId())) {
						return;
					}
				}
			}
			// If the session is null, a new one will be created by the parent class,
			// exceeding the allowed number
		}
		boolean exceptionIfMaximumExceeded = 
				strategyService.getStrategyBooleanValue("IS_KICK_SESSION") == false;
		allowableSessionsExceeded(exceptionIfMaximumExceeded,sessions,userSessionCount,sessionRegistry);		
	}
	
	protected void allowableSessionsExceeded(boolean exceptionIfMaximumExceeded,List<SessionInformation> sessions,
			int allowableSessions, SessionRegistry registry)
			throws SessionAuthenticationException {
		
		if (exceptionIfMaximumExceeded || (sessions == null)) {
			throw new SessionAuthenticationException(messages.getMessage(
					"ConcurrentSessionControlAuthenticationStrategy.exceededAllowed",
					new Object[] {allowableSessions},
					"Maximum sessions of {0} for this principal exceeded"));
		}

		// Determine least recently used sessions, and mark them for invalidation
		sessions.sort(Comparator.comparing(SessionInformation::getLastRequest));
		int maximumSessionsExceededBy = sessions.size() - allowableSessions + 1;
		List<SessionInformation> sessionsToBeExpired = sessions.subList(0, maximumSessionsExceededBy);
		for (SessionInformation session: sessionsToBeExpired) {
			
			repository.deleteById(session.getSessionId());
			//sessionRegistry.removeSessionInformation(session.getSessionId());
			//sessionService.deleteSessionById(session.getSessionId());
		}
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		Assert.notNull(messageSource, "messageSource cannot be null");
		this.messages = new MessageSourceAccessor(messageSource);
	}
}
