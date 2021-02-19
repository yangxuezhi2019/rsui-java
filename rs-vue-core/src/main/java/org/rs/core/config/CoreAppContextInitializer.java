package org.rs.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class CoreAppContextInitializer 
	implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered{

	@Override
	public int getOrder() {

		return HIGHEST_PRECEDENCE;
	}
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		
		applicationContext.addBeanFactoryPostProcessor(beanFactoryPostProcessor);
	}
	
	protected BeanFactoryPostProcessor beanFactoryPostProcessor = new BeanFactoryPostProcessor() {

		@Override
		public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			
			//BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)beanFactory;
		}		
	};
}
