package org.rs.core.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class RsMetadataSourceEvent extends ApplicationEvent{

	public RsMetadataSourceEvent(Object source) {
		super(source);
	}

	public RsMetadataSourceEvent() {
		super(new Object());
	}
}
