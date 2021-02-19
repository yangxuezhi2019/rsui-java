package org.rs.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class RsSecurityEventPublisher implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher applicationEventPublisher;
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	/**
	 * 发送重新加载授权信息事件
	 * */
	public void refreshMetadataSource() {
		
		ApplicationEvent event = new RsMetadataSourceEvent();
		applicationEventPublisher.publishEvent(event);
	}
}
