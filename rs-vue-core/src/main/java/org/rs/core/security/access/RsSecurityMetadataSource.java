package org.rs.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.rs.core.beans.model.RsUrlRolesModel;
import org.rs.core.event.RsMetadataSourceEvent;
import org.rs.core.mapper.RsRefreshMapper;
import org.rs.core.mapper.RsUrlModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public class RsSecurityMetadataSource 
	implements FilterInvocationSecurityMetadataSource,InitializingBean,ApplicationListener<RsMetadataSourceEvent>{
	//permitAll rememberMe anonymous authenticated fullyAuthenticated denyAll
	//hasAnyRole hasRole hasAnyAuthority hasIpAddress 
	//static String[] permitUrl = new String[] {"/","/key","/res/**","/favicon.ico","/comm/**","/error"};
	static String[] permitUrl = new String[] {"/**"};
	private ExpressionParser expressionParser = new SpelExpressionParser();
	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> defaultMap = new LinkedHashMap<>();
	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
	//默认的访问方式
	private RsConfigAttribute defaultConfigAttribute;
	private ReentrantReadWriteLock syncRequestMap = new ReentrantReadWriteLock();
	@Autowired
	private RsUrlModelMapper urlModelMapper;
	@Autowired
	private RsRefreshMapper refreshMapper;
	
	public RsSecurityMetadataSource() {
		
		//defaultConfigAttribute = new RsConfigAttribute(expressionParser.parseExpression("denyAll"));
		defaultConfigAttribute = new RsConfigAttribute(expressionParser.parseExpression("permitAll"));
		initDefaultMap();
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		List<ConfigAttribute> attributes = new ArrayList<>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : defaultMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				attributes.addAll(entry.getValue());
			}
		}
		
		syncRequestMap.readLock().lock();
		try {
			for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
				if (entry.getKey().matches(request)) {
					attributes.addAll(entry.getValue());
				}
			}
		}finally {
			syncRequestMap.readLock().unlock();
		}
		attributes.add(defaultConfigAttribute);
		return attributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : defaultMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	private void initDefaultMap() {
		
		for( String url: permitUrl ) {
			
			defaultMap.put(new AntPathRequestMatcher(url), RsConfigAttribute.createList(expressionParser.parseExpression("permitAll")));
		}
		//defaultMap.put(new AntPathRequestMatcher("/**"), RsConfigAttribute.createList());
	}
	
	private void initRequestMap() {
		
		List<RsUrlRolesModel> list = queryUrlRolesAllList();
		syncRequestMap.writeLock().lock();
		try {
			requestMap.clear();
			for( RsUrlRolesModel item: list ) {
				
				String url = item.getUrl();
				int urllx = item.getUrllx();
				List<String> roleStrList = item.getRoles().stream().map((r)->{return r.getJsnm();}).collect(Collectors.toList());
				if( urllx == 0 ) {
					
					if( roleStrList.size() == 0 ||
						!roleStrList.stream().anyMatch(r->{return r.equals("ROLE_SYSADMIN");})) {
						roleStrList.add("ROLE_SYSADMIN");
					}
					String str = StringUtils.collectionToDelimitedString(roleStrList,"','");
					String expression = "hasAnyRole('" + str + "')";
					requestMap.put(new AntPathRequestMatcher(url), RsConfigAttribute.createList(expressionParser.parseExpression(expression)));
					//String[] roles = item.getRoles().stream().map((r)->{return r.getJsnm();}).toArray(String[]::new);
				}else {
					if( roleStrList.size() > 0 ) {
						String str = StringUtils.collectionToDelimitedString(roleStrList,"','");
						String expression = "hasAnyRole('" + str + "')";
						requestMap.put(new AntPathRequestMatcher(url), RsConfigAttribute.createList(expressionParser.parseExpression(expression)));
					}
				}
			}
		}finally {
			syncRequestMap.writeLock().unlock();
		}		
	}
	
	@Override
	public void onApplicationEvent(RsMetadataSourceEvent event) {
		initRequestMap();
		refreshMapper.updateRefresh("SPRING_SECURITY_CONTEXT", System.currentTimeMillis());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		Long value = refreshMapper.getRefresh("SPRING_SECURITY_CONTEXT");
		if( value == null) {
			refreshMapper.insertRefresh("SPRING_SECURITY_CONTEXT", System.currentTimeMillis());
		}
		initRequestMap();
	}
	
	public List<RsUrlRolesModel> queryUrlRolesAllList(){
		Map<String,Object> param = new HashMap<>();
		param.put("orderCause", "ORDER BY URLLX");
		return urlModelMapper.queryUrlRolesList(param, null);
	}
}
