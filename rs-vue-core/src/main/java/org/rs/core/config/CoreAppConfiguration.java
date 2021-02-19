package org.rs.core.config;

import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.key.RsKeyGeneratorImpl;
import org.rs.core.page.RsPageInterceptor;
import org.rs.core.transaction.RsAspectJExpressionPointcut;
import org.rs.core.transaction.RsTransactionAttributeSource;
import org.rs.core.utils.RsJsonNullSerializer;

@Configuration(proxyBeanMethods = false)
@MapperScan(basePackages = {"org.rs.core.mapper"})
public class CoreAppConfiguration implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	private TransactionManager transactionManager;
	private TransactionInterceptor transactionInterceptor;
	private Environment env;
	private List<SqlSessionFactory> SqlSessionFactoryList;
	
	public CoreAppConfiguration(
			TransactionManager transactionManager,TransactionInterceptor transactionInterceptor, 
			Environment env, ObjectProvider<List<SqlSessionFactory>> sqlSessionFactoryProvider) {
		
		this.transactionManager = transactionManager;
		this.transactionInterceptor = transactionInterceptor;
		this.env = env;
		this.SqlSessionFactoryList = sqlSessionFactoryProvider.getIfAvailable();
	}
	
	@Bean
	public RsKeyGenerator RsKeyGenerator( DataSource dataSource ) {
		
		return new RsKeyGeneratorImpl(dataSource);
	}
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
	
	@Bean
	public DefaultBeanFactoryPointcutAdvisor rsTransactionAdvisor() {

		RsTransactionAttributeSource rsTransactionAttributeSource 
			= new RsTransactionAttributeSource("^(?:save|insert|update|add|valid|invalid|create|delete|del|put|remove|refresh)(?:.*?)$");
		TransactionInterceptor rsTransactionInterceptor = new TransactionInterceptor();
		rsTransactionInterceptor.setTransactionAttributeSource(rsTransactionAttributeSource);
		
		rsTransactionInterceptor.setTransactionManager(transactionManager);
		rsTransactionInterceptor.afterPropertiesSet();
		
		String expression = "execution(* org.rs..service..*.*(..))";
		
		RsAspectJExpressionPointcut pointcut = new RsAspectJExpressionPointcut();
		pointcut.setExpression(expression);
		DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
		advisor.setPointcut(pointcut);
		advisor.setAdvice(rsTransactionInterceptor);
		return advisor;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@PostConstruct
	public void customerBean() {
		
		ObjectProvider<ObjectMapper> objectMapperProvider = applicationContext.getBeanProvider(ObjectMapper.class);
		objectMapperProvider.forEach((item)->{
			item.getSerializerProvider().setNullValueSerializer(RsJsonNullSerializer.instance);
		});
	}
	
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = Type.SERVLET)
	@ConditionalOnProperty(prefix = "spring.apr", name = "enabled", havingValue = "true", matchIfMissing = false)
	static public class RsWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory>, Ordered{

		@Override
		public int getOrder() {
			return Ordered.LOWEST_PRECEDENCE;
		}

		@Override
		public void customize(ConfigurableTomcatWebServerFactory factory) {
			
			if( factory instanceof TomcatServletWebServerFactory ) {
				
				TomcatServletWebServerFactory tomcat = (TomcatServletWebServerFactory)factory;
				tomcat.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
				tomcat.addContextLifecycleListeners(new AprLifecycleListener());
			}			
		}		
	};
	
	@PostConstruct
	void postConstruct() {
		
		if( transactionInterceptor != null ) {
			transactionInterceptor.setTransactionManager(transactionManager);
		}		
		
		String dbType = env.getProperty("mybatis.dbType","mysql").toUpperCase();
		RsPageInterceptor interceptor = RsPageInterceptor.createInterceptor(dbType);
		for( SqlSessionFactory item : SqlSessionFactoryList ) {
			
			item.getConfiguration().addInterceptor(interceptor);
		}
		
		/*Map<String, AbstractTransactionManagementConfiguration> map1 = applicationContext.getBeansOfType(AbstractTransactionManagementConfiguration.class);
		System.out.println(map1);
		Map<String, AutoProxyRegistrar> map2 = applicationContext.getBeansOfType(AutoProxyRegistrar.class);
		System.out.println(map2);
		String names[] = applicationContext.getBeanDefinitionNames();
		for( String name : names ) {
			System.out.println(name);
		}*/
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.applicationContext = applicationContext;
	}
}
