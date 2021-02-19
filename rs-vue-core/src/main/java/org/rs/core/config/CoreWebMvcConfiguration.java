package org.rs.core.config;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import org.rs.core.log.RsLogPointcut;
import org.rs.core.security.filter.RsXssSecurityFilter;
import org.rs.core.log.RsLogInterceptor;
import org.rs.core.service.RsLogService;
import org.rs.core.service.RsUrlService;
import org.rs.core.web.RsErrorCtrler;
import org.rs.core.web.RsHandlerExceptionResolver;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class CoreWebMvcConfiguration {
	
	private final ServerProperties serverProperties;
	
	public CoreWebMvcConfiguration(ServerProperties serverProperties) {
		
		this.serverProperties = serverProperties;
	}
	
	@Bean
	public FilterRegistrationBean<RsXssSecurityFilter> xssFilterRegistration() {
	    FilterRegistrationBean<RsXssSecurityFilter> registration = new FilterRegistrationBean<>();
	    registration.setDispatcherTypes(DispatcherType.REQUEST);
	    registration.setFilter(new RsXssSecurityFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("rsXssFilter");
	    registration.setOrder(Integer.MIN_VALUE);
	    return registration;
	}
	
	@Bean
	public DefaultPointcutAdvisor rsLogAdvisor(RsLogService logService,ObjectMapper jsonMapper) {
		
		RsLogInterceptor advice = new RsLogInterceptor(logService,jsonMapper);
		RsLogPointcut pointcut = new RsLogPointcut();
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(pointcut);
		advisor.setAdvice(advice);
		return advisor;
	}
	
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({ResourceProperties.class })
	public static class RsWebMvcAutoConfigurationAdapter implements WebMvcConfigurer{
		
		@Autowired
		private ObjectMapper jsonMapper;
		private final ResourceProperties resourceProperties;
		
		public RsWebMvcAutoConfigurationAdapter(ResourceProperties resourceProperties) {
			
			this.resourceProperties = resourceProperties;
		}
		@Override
		public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
			
			//WebMvcConfigurer.super.configureHandlerExceptionResolvers(resolvers);
			resolvers.add(new RsHandlerExceptionResolver(jsonMapper));
		}
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
			
			//WebMvcConfigurer.super.configureMessageConverters(converters);
			messageConverters.add(new ByteArrayHttpMessageConverter());
			//messageConverters.add(new StringHttpMessageConverter());
			messageConverters.add(new ResourceHttpMessageConverter());
			messageConverters.add(new ResourceRegionHttpMessageConverter());
			try {
				messageConverters.add(new SourceHttpMessageConverter<>());
			}
			catch (Throwable ex) {
				// Ignore when no TransformerFactory implementation is available...
			}
			//messageConverters.add(new AllEncompassingFormHttpMessageConverter());
			//messageConverters.add(new MappingJackson2HttpMessageConverter(jsonMapper));
		}
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
			Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
			CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
			if (!registry.hasMappingForPattern("/favicon.ico")) {
					registry.addResourceHandler("/favicon.ico")
					.addResourceLocations("classpath:/static/")
					.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl);
			}
		}
		
		private Integer getSeconds(Duration cachePeriod) {
			return (cachePeriod != null) ? (int) cachePeriod.getSeconds() : null;
		}
	}
	
	@Bean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		
		return new HttpMessageConverters(false,converters.orderedStream().collect(Collectors.toList()));
	}
	
	@Bean
	public RsErrorCtrler rsErrorController(ErrorAttributes errorAttributes,
			ObjectProvider<ErrorViewResolver> errorViewResolvers) {
		return new RsErrorCtrler(errorAttributes, this.serverProperties.getError(),
				errorViewResolvers.orderedStream().collect(Collectors.toList()));
	}
	
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = Type.SERVLET)
	public static class CoreGenericApplicationListener implements GenericApplicationListener{

		private RsUrlService urlService;
		public CoreGenericApplicationListener(RsUrlService urlService) {
			
			this.urlService = urlService;
		}
		@Override
		public void onApplicationEvent(ApplicationEvent event) {
			
			urlService.refreshUrls();
		}

		@Override
		public boolean supportsEventType(ResolvableType eventType) {
			
			return ContextRefreshedEvent.class.isAssignableFrom(eventType.getRawClass());
		}
		
	}
}
