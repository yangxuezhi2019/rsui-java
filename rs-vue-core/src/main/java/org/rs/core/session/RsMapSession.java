package org.rs.core.session;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.session.Session;

public class RsMapSession implements Session, Serializable{

	private static final long serialVersionUID = 7160779239673823561L;
	/**
	 * Default {@link #setMaxInactiveInterval(Duration)} (30 minutes).
	 */
	public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 1800;

	private String id;

	private final String originalId;

	private Map<String, Object> sessionAttrs = new HashMap<>();

	private Instant creationTime = Instant.now();

	private Instant lastAccessedTime = this.creationTime;

	/**
	 * Defaults to 30 minutes.
	 */
	private Duration maxInactiveInterval = Duration.ofSeconds(DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);

	/**
	 * Creates a new instance with a secure randomly generated identifier.
	 */
	public RsMapSession() {
		this(generateId());
	}

	/**
	 * Creates a new instance with the specified id. This is preferred to the default
	 * constructor when the id is known to prevent unnecessary consumption on entropy
	 * which can be slow.
	 * @param id the identifier to use
	 */
	public RsMapSession(String id) {
		this.id = id;
		this.originalId = id;
	}

	/**
	 * Creates a new instance from the provided {@link Session}.
	 * @param session the {@link Session} to initialize this {@link Session} with. Cannot
	 * be null.
	 */
	public RsMapSession(Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		this.id = session.getId();
		this.originalId = this.id;
		this.sessionAttrs = new HashMap<>(session.getAttributeNames().size());
		for (String attrName : session.getAttributeNames()) {
			Object attrValue = session.getAttribute(attrName);
			if (attrValue != null) {
				this.sessionAttrs.put(attrName, attrValue);
			}
		}
		this.lastAccessedTime = session.getLastAccessedTime();
		this.creationTime = session.getCreationTime();
		this.maxInactiveInterval = session.getMaxInactiveInterval();
	}

	@Override
	public Instant getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * Sets the time that this {@link Session} was created. The default is when the
	 * {@link Session} was instantiated.
	 * @param creationTime the time that this {@link Session} was created.
	 */
	public void setCreationTime(Instant creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Sets the identifier for this {@link Session}. The id should be a secure random
	 * generated value to prevent malicious users from guessing this value. The default is
	 * a secure random generated identifier.
	 * @param id the identifier for this session.
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Get the original session id.
	 * @return the original session id
	 * @see #changeSessionId()
	 */
	public String getOriginalId() {
		return this.originalId;
	}

	@Override
	public String changeSessionId() {
		String changedId = generateId();
		setId(changedId);
		return changedId;
	}

	@Override
	public Instant getLastAccessedTime() {
		return this.lastAccessedTime;
	}

	@Override
	public void setLastAccessedTime(Instant lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	
	@Override
	public Duration getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}
	
	@Override
	public void setMaxInactiveInterval(Duration interval) {
		this.maxInactiveInterval = interval;
	}

	@Override
	public boolean isExpired() {
		return isExpired(Instant.now());
	}

	boolean isExpired(Instant now) {
		if (this.maxInactiveInterval.isNegative()) {
			return false;
		}
		return now.minus(this.maxInactiveInterval).compareTo(this.lastAccessedTime) >= 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String attributeName) {
		return (T) this.sessionAttrs.get(attributeName);
	}

	@Override
	public void setAttribute(String attributeName, Object attributeValue) {
		if (attributeValue == null) {
			removeAttribute(attributeName);
		}
		else {
			this.sessionAttrs.put(attributeName, attributeValue);
		}
	}

	@Override
	public void removeAttribute(String attributeName) {
		this.sessionAttrs.remove(attributeName);
	}
	
	@Override
	public Set<String> getAttributeNames() {
		return new HashSet<>(this.sessionAttrs.keySet());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Session && this.id.equals(((Session) obj).getId());
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	private static String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
