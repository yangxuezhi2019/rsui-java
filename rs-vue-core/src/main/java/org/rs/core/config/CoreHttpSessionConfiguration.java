package org.rs.core.config;

import javax.annotation.PostConstruct;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.rs.core.service.RsSessionService;
import org.rs.core.session.RsSessionRepository;
import org.rs.core.session.jdbc.RsIndexedSessionRepository;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.FlushMode;
import org.springframework.session.IndexResolver;
import org.springframework.session.SaveMode;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties({ ServerProperties.class })
public class CoreHttpSessionConfiguration extends SpringHttpSessionConfiguration
	implements BeanClassLoaderAware {

	private FlushMode flushMode = FlushMode.ON_SAVE;
	private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;
	private IndexResolver<Session> indexResolver;
	private ConversionService springSessionConversionService;
	private ConversionService conversionService;
	private ClassLoader classLoader;

	//private StringValueResolver embeddedValueResolver;
	@Bean
	public DefaultCookieSerializer cookieSerializer() {
		
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setCookieMaxAge(-1);
		cookieSerializer.setCookieName("RSESSIONID");
		cookieSerializer.setCookiePath("/");
		return cookieSerializer;
	}
	
	@Configuration(proxyBeanMethods = false)
	//@ConditionalOnProperty(prefix = "config.session", name = "type", havingValue="jdbc", matchIfMissing = false)
	public class JdbcHttpSessionConfiguration{
		
		@Bean
		public FindByIndexNameSessionRepository<?> sessionRepository(ServerProperties serverProperties,ApplicationContext ctx) {
			
			RsSessionService sessionService = ctx.getBean(RsSessionService.class);
			int sessionTimeout = (int) serverProperties.getServlet().getSession().getTimeout().getSeconds();
			RsIndexedSessionRepository sessionRepository = new RsIndexedSessionRepository(sessionService);
			sessionRepository.setDefaultMaxInactiveInterval(sessionTimeout);
			sessionRepository.setFlushMode(CoreHttpSessionConfiguration.this.flushMode);
			sessionRepository.setSaveMode(CoreHttpSessionConfiguration.this.saveMode);
			if (CoreHttpSessionConfiguration.this.indexResolver != null) {
				sessionRepository.setIndexResolver(CoreHttpSessionConfiguration.this.indexResolver);
			}
			if (CoreHttpSessionConfiguration.this.springSessionConversionService != null) {
				sessionRepository.setConversionService(CoreHttpSessionConfiguration.this.springSessionConversionService);
			}
			else if (CoreHttpSessionConfiguration.this.conversionService != null) {
				sessionRepository.setConversionService(CoreHttpSessionConfiguration.this.conversionService);
			}
			else {
				sessionRepository.setConversionService(createConversionServiceWithBeanClassLoader(CoreHttpSessionConfiguration.this.classLoader));
			}
			return sessionRepository;
		}
	}
	
	/*@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(prefix = "config.session", name = "type", havingValue="redis", matchIfMissing = false)
	public class RedisHttpSessionConfiguration{
		
		private RedisSerializer<Object> defaultRedisSerializer;
		private RedisConnectionFactory redisConnectionFactory;
		private List<SessionRepositoryCustomizer<RsRedisIndexedSessionRepository>> sessionRepositoryCustomizers;
		private ApplicationEventPublisher applicationEventPublisher;
		
		@Bean
		public FindByIndexNameSessionRepository<?> sessionRepository(ServerProperties serverProperties) {
			
			RedisTemplate<Object, Object> redisTemplate = createRedisTemplate();			
			RsRedisIndexedSessionRepository sessionRepository = new RsRedisIndexedSessionRepository(redisTemplate);
			sessionRepository.setApplicationEventPublisher(this.applicationEventPublisher);
			if (CoreHttpSessionConfiguration.this.indexResolver != null) {
				sessionRepository.setIndexResolver(CoreHttpSessionConfiguration.this.indexResolver);
			}
			if (this.defaultRedisSerializer != null) {
				sessionRepository.setDefaultSerializer(this.defaultRedisSerializer);
			}
			//sessionRepository.setDefaultMaxInactiveInterval(this.maxInactiveIntervalInSeconds);
			if (StringUtils.hasText(RsRedisIndexedSessionRepository.DEFAULT_NAMESPACE)) {
				sessionRepository.setRedisKeyNamespace(RsRedisIndexedSessionRepository.DEFAULT_NAMESPACE);
			}
			sessionRepository.setFlushMode(CoreHttpSessionConfiguration.this.flushMode);
			sessionRepository.setSaveMode(CoreHttpSessionConfiguration.this.saveMode);
			int database = resolveDatabase();
			sessionRepository.setDatabase(database);
			this.sessionRepositoryCustomizers
					.forEach((sessionRepositoryCustomizer) -> sessionRepositoryCustomizer.customize(sessionRepository));
			return sessionRepository;
		}
		
		@Autowired
		public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
			this.applicationEventPublisher = applicationEventPublisher;
		}
		
		@Autowired
		public void setRedisConnectionFactory(
				@SpringSessionRedisConnectionFactory ObjectProvider<RedisConnectionFactory> springSessionRedisConnectionFactory,
				ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
			RedisConnectionFactory redisConnectionFactoryToUse = springSessionRedisConnectionFactory.getIfAvailable();
			if (redisConnectionFactoryToUse == null) {
				redisConnectionFactoryToUse = redisConnectionFactory.getObject();
			}
			this.redisConnectionFactory = redisConnectionFactoryToUse;
		}
		
		@Autowired(required = false)
		public void setSessionRepositoryCustomizer(
				ObjectProvider<SessionRepositoryCustomizer<RsRedisIndexedSessionRepository>> sessionRepositoryCustomizers) {
			this.sessionRepositoryCustomizers = sessionRepositoryCustomizers.orderedStream().collect(Collectors.toList());
		}
		
		private RedisTemplate<Object, Object> createRedisTemplate() {
			RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.setHashKeySerializer(new StringRedisSerializer());
			if (this.defaultRedisSerializer != null) {
				redisTemplate.setDefaultSerializer(this.defaultRedisSerializer);
			}
			redisTemplate.setConnectionFactory(this.redisConnectionFactory);
			redisTemplate.setBeanClassLoader(CoreHttpSessionConfiguration.this.classLoader);
			redisTemplate.afterPropertiesSet();
			return redisTemplate;
		}
		
		private int resolveDatabase() {
			if (ClassUtils.isPresent("io.lettuce.core.RedisClient", null)
					&& this.redisConnectionFactory instanceof LettuceConnectionFactory) {
				return ((LettuceConnectionFactory) this.redisConnectionFactory).getDatabase();
			}
			if (ClassUtils.isPresent("redis.clients.jedis.Jedis", null)
					&& this.redisConnectionFactory instanceof JedisConnectionFactory) {
				return ((JedisConnectionFactory) this.redisConnectionFactory).getDatabase();
			}
			return RsRedisIndexedSessionRepository.DEFAULT_DATABASE;
		}
	}*/

	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}

	public void setSaveMode(SaveMode saveMode) {
		this.saveMode = saveMode;
	}

	@Autowired(required = false)
	public void setIndexResolver(IndexResolver<Session> indexResolver) {
		this.indexResolver = indexResolver;
	}

	@Autowired(required = false)
	@Qualifier("springSessionConversionService")
	public void setSpringSessionConversionService(ConversionService conversionService) {
		this.springSessionConversionService = conversionService;
	}

	@Autowired(required = false)
	@Qualifier("conversionService")
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	private static GenericConversionService createConversionServiceWithBeanClassLoader(ClassLoader classLoader) {
		GenericConversionService conversionService = new GenericConversionService();
		conversionService.addConverter(Object.class, byte[].class, new SerializingConverter());
		conversionService.addConverter(byte[].class, Object.class, new DeserializingConverter(classLoader));
		return conversionService;
	}

	/**
	 * Configuration of scheduled job for cleaning up expired sessions.
	 */
	//@EnableScheduling
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(prefix = "config.session", name = "cleanup", matchIfMissing = false)
	class SessionCleanupConfiguration {
	
		private Scheduler scheduler;
		private Environment env;
		private ApplicationContext ctx;
		
		SessionCleanupConfiguration( Scheduler scheduler, Environment env,ApplicationContext ctx) {
			this.scheduler = scheduler;
			this.env = env;
			this.ctx = ctx;
		}
	
		@PostConstruct
		public void init() throws Exception {
			
			JobDetailFactoryBean jobBean = new JobDetailFactoryBean();
			jobBean.setJobClass(SessionCleanupJob.class);
			jobBean.setDurability(true);
			jobBean.setApplicationContext(ctx);
			jobBean.afterPropertiesSet();
			
			String cronExpression = env.getProperty("config.session.cleanupCron","0 45 * * * ?");
			CronTriggerFactoryBean triggerBean = new CronTriggerFactoryBean();
		    JobDetail jobDetail = jobBean.getObject();
		    triggerBean.setJobDetail(jobDetail); // 设置 jobDetail
		    triggerBean.setStartDelay(3000); // 任务启动延迟2秒执行
		    triggerBean.setCronExpression(cronExpression);
		    triggerBean.afterPropertiesSet();
		    Trigger trigger = triggerBean.getObject();
		    scheduler.scheduleJob(jobDetail,trigger);
		}
	}
	
	@PersistJobDataAfterExecution
	@DisallowConcurrentExecution
	static public class SessionCleanupJob extends QuartzJobBean {

		@Autowired
		private RsSessionRepository<?> sessionRepository;
		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			
			if( sessionRepository != null ) {
				
				sessionRepository.cleanUpExpiredSessions();
			}
		}
	}
}
