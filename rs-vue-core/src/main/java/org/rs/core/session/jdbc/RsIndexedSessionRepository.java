package org.rs.core.session.jdbc;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.rs.core.beans.RsSession;
import org.rs.core.beans.RsSessionAttribute;
import org.rs.core.beans.model.RsSessionModel;
import org.rs.core.service.RsSessionService;
import org.rs.core.session.RsIndexResolver;
import org.rs.core.session.RsMapSession;
import org.rs.core.session.RsSessionRepository;
import org.rs.core.utils.CoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.FlushMode;
import org.springframework.session.IndexResolver;
import org.springframework.session.SaveMode;
import org.springframework.session.Session;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.Assert;

public class RsIndexedSessionRepository 
	implements RsSessionRepository<RsIndexedSessionRepository.JdbcSession>	{

	private static final Logger logger = LoggerFactory.getLogger(RsIndexedSessionRepository.class);
	private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	/**
	 * If non-null, this value is used to override the default value for
	 * {@link JdbcSession#setMaxInactiveInterval(Duration)}.
	 */
	private Integer defaultMaxInactiveInterval;
	private IndexResolver<Session> indexResolver = new RsIndexResolver<>();
	private ConversionService conversionService = createDefaultConversionService();
	private FlushMode flushMode = FlushMode.ON_SAVE;
	private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;
	private final RsSessionService sessionService;
	/**
	 * Create a new {@link JdbcIndexedSessionRepository} instance which uses the provided
	 * {@link JdbcOperations} and {@link TransactionOperations} to manage sessions.
	 * @param jdbcOperations the {@link JdbcOperations} to use
	 * @param transactionOperations the {@link TransactionOperations} to use
	 */
	public RsIndexedSessionRepository(RsSessionService sessionService) {
		
		this.sessionService = sessionService;
	}

	/**
	 * Set the maximum inactive interval in seconds between requests before newly created
	 * sessions will be invalidated. A negative time indicates that the session will never
	 * timeout. The default is 1800 (30 minutes).
	 * @param defaultMaxInactiveInterval the maximum inactive interval in seconds
	 */
	public void setDefaultMaxInactiveInterval(Integer defaultMaxInactiveInterval) {
		this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
	}

	/**
	 * Set the {@link IndexResolver} to use.
	 * @param indexResolver the index resolver
	 */
	public void setIndexResolver(IndexResolver<Session> indexResolver) {
		Assert.notNull(indexResolver, "indexResolver cannot be null");
		this.indexResolver = indexResolver;
	}

	/**
	 * Sets the {@link ConversionService} to use.
	 * @param conversionService the converter to set
	 */
	public void setConversionService(ConversionService conversionService) {
		Assert.notNull(conversionService, "conversionService must not be null");
		this.conversionService = conversionService;
	}

	/**
	 * Set the flush mode. Default is {@link FlushMode#ON_SAVE}.
	 * @param flushMode the flush mode
	 */
	public void setFlushMode(FlushMode flushMode) {
		Assert.notNull(flushMode, "flushMode must not be null");
		this.flushMode = flushMode;
	}

	/**
	 * Set the save mode.
	 * @param saveMode the save mode
	 */
	public void setSaveMode(SaveMode saveMode) {
		Assert.notNull(saveMode, "saveMode must not be null");
		this.saveMode = saveMode;
	}
	
	private RsMapSession createRsMapSession( RsSessionModel model ) {
		
		RsMapSession delegate = new RsMapSession(model.getSession_id());
		delegate.setCreationTime(Instant.ofEpochMilli(model.getCreation_time()));
		delegate.setLastAccessedTime(Instant.ofEpochMilli(model.getLast_access_time()));
		delegate.setMaxInactiveInterval(Duration.ofSeconds(model.getMax_inactive_interval()));
		
		List<RsSessionAttribute> attributes = model.getSessionAttrs();
		for( RsSessionAttribute attribute : attributes ) {
			
			delegate.setAttribute(attribute.getAttribute_name(), lazily(() -> deserialize(attribute.getAttribute_bytes())));
		}
		return delegate;
	}
	
	private RsSession createRsSession( JdbcSession session ) {
		
		Map<String, String> indexes = indexResolver.resolveIndexesFor(session);
		RsSession bean = new RsSession();
		bean.setPrimary_id(session.primaryKey);
		bean.setSession_id(session.getId());
		bean.setCreation_time(session.getCreationTime().toEpochMilli());
		bean.setLast_access_time(session.getLastAccessedTime().toEpochMilli());
		bean.setMax_inactive_interval((int) session.getMaxInactiveInterval().getSeconds());
		bean.setExpiry_time(session.getExpiryTime().toEpochMilli());
		bean.setIp_addr(session.getIp());
		bean.setPrincipal_name(indexes.get("yhbh"));
		return bean;
	}

	@Override
	public JdbcSession createSession() {
		RsMapSession delegate = new RsMapSession();
		if (this.defaultMaxInactiveInterval != null) {
			delegate.setMaxInactiveInterval(Duration.ofSeconds(this.defaultMaxInactiveInterval));
		}
		HttpServletRequest request = CoreUtils.getHttpServletRequest();
		String ip = CoreUtils.getIpAddr(request);
		String primaryId = UUID.randomUUID().toString().replaceAll("-", "");
		JdbcSession session = new JdbcSession(delegate, primaryId ,ip, true );
		session.flushIfRequired();
		logger.debug("Create session: {}", session.getId());
		return session;
	}

	@Override
	public void save(final JdbcSession session) {
		
		logger.debug("Save session: {}", session.getId());
		//logger.debug("Save session:", new RuntimeException("For debugging purposes only (not an error)"));
		session.save();
	}

	@Override
	public JdbcSession findById(final String id) {
		
		//logger.debug("find session:", new RuntimeException("For debugging purposes only (not an error)"));
		RsSessionModel result = sessionService.findBySessionId(id);
		if( result == null )
			return null;
		
		RsMapSession delegate = createRsMapSession(result);
		final JdbcSession session = new JdbcSession(delegate, result.getPrimary_id(),result.getIp_addr(), false );
		if (session != null) {
			if (session.isExpired()) {
				deleteById(id);
			}
			else {
				return session;
			}
		}
		return session;
	}

	@Override
	public void deleteById(final String id) {
		sessionService.deleteBySessionId(id);
	}

	@Override
	public Map<String, JdbcSession> findByIndexNameAndIndexValue(String indexName, final String indexValue) {
		if (!PRINCIPAL_NAME_INDEX_NAME.equals(indexName)) {
			return Collections.emptyMap();
		}
		
		List<RsSessionModel> sessions = sessionService.findByPrincipalName(indexValue);		
		Map<String, JdbcSession> sessionMap = new HashMap<>(sessions.size());
		
		for (RsSessionModel result : sessions) {
			
			RsMapSession delegate = createRsMapSession(result);
			JdbcSession session = new JdbcSession(delegate, result.getPrimary_id(),result.getIp_addr(), false );
			sessionMap.put(session.getId(), session);
		}		
		return sessionMap;
	}
	
	private void insertSession(JdbcSession session) {
		
		RsSession bean = createRsSession(session);
		Set<String> attributeNames = session.getAttributeNames();		
		List<RsSessionAttribute> attributes = consctructSessionAttributes(session,new ArrayList<>(attributeNames),true);
		sessionService.insertSessionAndAttributes(bean,attributes);
	}
	
	private void updateSession(JdbcSession session) {
		
		RsSession bean = createRsSession(session);		
		List<String> addedAttributeNames = session.delta.entrySet().stream()
				.filter((entry) -> entry.getValue() == RsDeltaValue.ADDED).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		List<RsSessionAttribute> insertAttributes = consctructSessionAttributes(session,addedAttributeNames,true);
		List<String> updatedAttributeNames = session.delta.entrySet().stream()
				.filter((entry) -> entry.getValue() == RsDeltaValue.UPDATED).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		List<RsSessionAttribute> updateAttributes = consctructSessionAttributes(session,updatedAttributeNames,true);
		List<String> removedAttributeNames = session.delta.entrySet().stream()
				.filter((entry) -> entry.getValue() == RsDeltaValue.REMOVED).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		List<RsSessionAttribute> deleteAttributes = consctructSessionAttributes(session,removedAttributeNames,false);
		
		sessionService.updateSessionAndAttributes(bean,insertAttributes,updateAttributes,deleteAttributes);
	}

	private List<RsSessionAttribute> consctructSessionAttributes(JdbcSession session, List<String> attributeNames, boolean isUpdate) {
		
		//Assert.notEmpty(attributeNames, "attributeNames must not be null or empty");		
		List<RsSessionAttribute> attributes = new ArrayList<>();
		for( String attributeName : attributeNames ) {
			
			RsSessionAttribute bean = new RsSessionAttribute();
			bean.setSession_primary_id(session.primaryKey);
			bean.setAttribute_name(attributeName);
			if( isUpdate )
				bean.setAttribute_bytes(serialize(session.getAttribute(attributeName)));
			attributes.add(bean);
		}
		return attributes;
	}

	public void cleanUpExpiredSessions() {
		
		int deletedCount = sessionService.deleteSessionByExpiryTime(System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.debug("Cleaned up " + deletedCount + " expired sessions");
		}
	}

	private static GenericConversionService createDefaultConversionService() {
		GenericConversionService converter = new GenericConversionService();
		converter.addConverter(Object.class, byte[].class, new SerializingConverter());
		converter.addConverter(byte[].class, Object.class, new DeserializingConverter());
		return converter;
	}
	
	private byte[] serialize(Object object) {
		return (byte[]) this.conversionService.convert(object, TypeDescriptor.valueOf(Object.class),
				TypeDescriptor.valueOf(byte[].class));
	}

	private Object deserialize(byte[] bytes) {
		return this.conversionService.convert(bytes, TypeDescriptor.valueOf(byte[].class),
				TypeDescriptor.valueOf(Object.class));
	}

	private static <T> Supplier<T> value(T value) {
		return (value != null) ? () -> value : null;
	}

	private static <T> Supplier<T> lazily(Supplier<T> supplier) {
		Supplier<T> lazySupplier = new Supplier<T>() {

			private T value;

			@Override
			public T get() {
				if (this.value == null) {
					this.value = supplier.get();
				}
				return this.value;
			}

		};

		return (supplier != null) ? lazySupplier : null;
	}

	/**
	 * The {@link Session} to use for {@link RsIndexedSessionRepository}.
	 *
	 * @author Vedran Pavic
	 */
	final class JdbcSession implements Session {

		private final Session delegate;

		private final String primaryKey;

		private boolean isNew;

		private boolean changed;
		
		private final String ip;

		private Map<String, RsDeltaValue> delta = new HashMap<>();

		JdbcSession(RsMapSession delegate, String primaryKey, String ip, boolean isNew ) {
			this.delegate = delegate;
			this.primaryKey = primaryKey;
			this.isNew = isNew;
			this.ip = ip;
			if (this.isNew || (RsIndexedSessionRepository.this.saveMode == SaveMode.ALWAYS)) {
				getAttributeNames().forEach((attributeName) -> this.delta.put(attributeName, RsDeltaValue.UPDATED));
			}
		}
		
		String getIp() {
			return ip;
		}

		boolean isNew() {
			return this.isNew;
		}

		boolean isChanged() {
			return this.changed;
		}

		Map<String, RsDeltaValue> getDelta() {
			return this.delta;
		}

		void clearChangeFlags() {
			this.isNew = false;
			this.changed = false;
			this.delta.clear();
		}

		Instant getExpiryTime() {
			return getLastAccessedTime().plus(getMaxInactiveInterval());
		}

		@Override
		public String getId() {
			return this.delegate.getId();
		}
		
		public void setChanged() {
			this.changed = true;
		}

		@Override
		public String changeSessionId() {
			this.changed = true;
			return this.delegate.changeSessionId();
		}

		@Override
		public <T> T getAttribute(String attributeName) {
			Supplier<T> supplier = this.delegate.getAttribute(attributeName);
			if (supplier == null) {
				return null;
			}
			T attributeValue = supplier.get();
			if (attributeValue != null
					&& RsIndexedSessionRepository.this.saveMode.equals(SaveMode.ON_GET_ATTRIBUTE)) {
				this.delta.put(attributeName, RsDeltaValue.UPDATED);
			}
			return attributeValue;
		}

		@Override
		public Set<String> getAttributeNames() {
			return this.delegate.getAttributeNames();
		}

		@Override
		public void setAttribute(String attributeName, Object attributeValue) {
			boolean attributeExists = (this.delegate.getAttribute(attributeName) != null);
			boolean attributeRemoved = (attributeValue == null);
			if (!attributeExists && attributeRemoved) {
				return;
			}
			if (attributeExists) {
				if (attributeRemoved) {
					this.delta.merge(attributeName, RsDeltaValue.REMOVED,
							(oldDeltaValue, deltaValue) -> (oldDeltaValue == RsDeltaValue.ADDED) ? null : deltaValue);
				}
				else {
					this.delta.merge(attributeName, RsDeltaValue.UPDATED, (oldDeltaValue,
							deltaValue) -> (oldDeltaValue == RsDeltaValue.ADDED) ? oldDeltaValue : deltaValue);
				}
			}
			else {
				this.delta.merge(attributeName, RsDeltaValue.ADDED, (oldDeltaValue,
						deltaValue) -> (oldDeltaValue == RsDeltaValue.ADDED) ? oldDeltaValue : RsDeltaValue.UPDATED);
			}
			this.delegate.setAttribute(attributeName, value(attributeValue));
			if (PRINCIPAL_NAME_INDEX_NAME.equals(attributeName) || SPRING_SECURITY_CONTEXT.equals(attributeName)) {
				this.changed = true;
			}
			flushIfRequired();
		}

		@Override
		public void removeAttribute(String attributeName) {
			setAttribute(attributeName, null);
		}

		@Override
		public Instant getCreationTime() {
			return this.delegate.getCreationTime();
		}

		@Override
		public void setLastAccessedTime(Instant lastAccessedTime) {
			this.delegate.setLastAccessedTime(lastAccessedTime);
			this.changed = true;
			flushIfRequired();
		}

		@Override
		public Instant getLastAccessedTime() {
			return this.delegate.getLastAccessedTime();
		}

		@Override
		public void setMaxInactiveInterval(Duration interval) {
			this.delegate.setMaxInactiveInterval(interval);
			this.changed = true;
			flushIfRequired();
		}

		@Override
		public Duration getMaxInactiveInterval() {
			return this.delegate.getMaxInactiveInterval();
		}

		@Override
		public boolean isExpired() {
			return this.delegate.isExpired();
		}

		private void flushIfRequired() {
			if (RsIndexedSessionRepository.this.flushMode == FlushMode.IMMEDIATE) {
				save();
			}
		}

		private void save() {
			if (this.isNew) {
				RsIndexedSessionRepository.this.insertSession(JdbcSession.this);
			}
			else {
				RsIndexedSessionRepository.this.updateSession(JdbcSession.this);
			}
			clearChangeFlags();
		}

	}
}
