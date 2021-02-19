package org.rs.core.config;

import org.rs.core.security.access.RsSecurityMetadataSource;
import org.rs.core.security.authentication.RsAuthenticationEventListener;
import org.rs.core.security.authentication.RsDaoAuthenticationProvider;
import org.rs.core.security.event.RsSecurityEventPublisher;
import org.rs.core.service.RsLogService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity(debug = true)
public class CoreWebSecurityEnablerConfiguration {

//	private static final Logger logger = LoggerFactory.getLogger(CoreWebSecurityEnablerConfiguration.class);
//	private static final String NOOP_PASSWORD_PREFIX = "{noop}";
//	private static final Pattern PASSWORD_ALGORITHM_PATTERN = Pattern.compile("^\\{.+}.*$");
//	private final ApplicationContext context;
	
	public CoreWebSecurityEnablerConfiguration() {
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(SecurityProperties properties,
			ObjectProvider<UserDetailsService> UserDetailsService, PasswordEncoder passwordEncoder) throws Exception {
		
		//PasswordEncoder pwdEncoder = passwordEncoder.getIfAvailable();
		/*SecurityProperties.User user = properties.getUser();
		List<String> roles = user.getRoles();
		UserDetailsService userDetailsService = new InMemoryUserDetailsManager(
				User.withUsername(user.getName()).password(getOrDeducePassword(user, pwdEncoder))
						.roles(StringUtils.toStringArray(roles)).build());*/
		//UserDetailsPasswordService passwordManager = getBeanOrNull(UserDetailsPasswordService.class);
		UserDetailsService userDetailsService = UserDetailsService.getIfAvailable();
		//RsUserDetailsManager rsUserDetailsManager = new RsUserDetailsManager();
		RsDaoAuthenticationProvider provider = new RsDaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		provider.afterPropertiesSet();
		
		return provider;
	}
	
	@Bean
	public RsSecurityMetadataSource rsSecurityMetadataSource() {
		return new RsSecurityMetadataSource();
	}
	
	@Bean
	public RsAuthenticationEventListener rsAuthenticationEventListener( RsLogService logService ) {
		
		return new RsAuthenticationEventListener(logService);
	}
	
	@Bean 
	public RsSecurityEventPublisher rsSecurityEventPublisher() {
		return new RsSecurityEventPublisher();
	}
	
	/*private String getOrDeducePassword(SecurityProperties.User user, PasswordEncoder encoder) {
		String password = user.getPassword();
		if (user.isPasswordGenerated()) {
			logger.info(String.format("%n%nUsing generated security password: %s%n", user.getPassword()));
		}
		if (encoder != null || PASSWORD_ALGORITHM_PATTERN.matcher(password).matches()) {
			return password;
		}
		return NOOP_PASSWORD_PREFIX + password;
	}
	
	private <T> T getBeanOrNull(Class<T> type) {
		String[] beanNames = context
				.getBeanNamesForType(type);
		if (beanNames.length != 1) {
			return null;
		}
	
		return context.getBean(beanNames[0], type);
	}*/
}
