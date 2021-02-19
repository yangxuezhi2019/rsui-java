package org.rs.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CoreAutoConfigurationImportFilter extends SpringBootCondition
	implements AutoConfigurationImportFilter, BeanFactoryAware, BeanClassLoaderAware, EnvironmentAware{

	@SuppressWarnings("unused")
	private Environment environment;
	private BeanFactory beanFactory;
	@SuppressWarnings("unused")
	private ClassLoader beanClassLoader;
	
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		
		this.environment = environment;
	}

	@Override
	public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
		
		ConditionEvaluationReport report = ConditionEvaluationReport.find(this.beanFactory);
		ConditionOutcome[] outcomes = getOutcomes(autoConfigurationClasses,autoConfigurationMetadata);
		boolean[] match = new boolean[outcomes.length];
		for (int i = 0; i < outcomes.length; i++) {
			match[i] = (outcomes[i] == null || outcomes[i].isMatch());
			if (!match[i] && outcomes[i] != null) {
				logOutcome(autoConfigurationClasses[i], outcomes[i]);
				if (report != null) {
					report.recordConditionEvaluation(autoConfigurationClasses[i], this, outcomes[i]);
				}
			}
		}
		return match;
	}
	//org.springframework.boot.autoconfigure.session.SessionAutoConfiguration
	private ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses,
			AutoConfigurationMetadata autoConfigurationMetadata) {
		ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
		for (int i = 0; i < outcomes.length; i++) {
			String autoConfigurationClass = autoConfigurationClasses[i];
			if (autoConfigurationClass != null ) {

				if( autoConfigurationClass.endsWith("autoconfigure.session.SessionAutoConfiguration") ){
					
					outcomes[i] = ConditionOutcome.noMatch( autoConfigurationClass + " is no match.");
				} /*else if( autoConfigurationClass.equalsIgnoreCase("org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration") ) {
					Boolean result = environment.getProperty("server.error.defaultError", Boolean.class);
					if( result != null && !result.booleanValue() ) {
						outcomes[i] = ConditionOutcome.noMatch( autoConfigurationClass + " is no match.");
					}
					}*/
			}
		}
		return outcomes;
	}

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		return ConditionOutcome.match("OK");
	}
}
