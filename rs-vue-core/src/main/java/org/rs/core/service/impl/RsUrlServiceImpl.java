package org.rs.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.rs.core.annotation.ApiInfo;
import org.rs.core.beans.RsUrl;
import org.rs.core.beans.model.RsUrlRolesModel;
import org.rs.core.mapper.RsRoleUrlMapper;
import org.rs.core.mapper.RsUrlMapper;
import org.rs.core.mapper.RsUrlModelMapper;
import org.rs.core.page.IPage;
import org.rs.core.service.RsUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Service
public class RsUrlServiceImpl implements RsUrlService{

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private RsUrlMapper urlMapper;
	@Autowired
	private RsRoleUrlMapper roleUrlMapper;
	@Autowired
	private RsUrlModelMapper urlModelMapper;
	@Override
	public void refreshUrls() {
		
		urlMapper.invalidUrl();
		//saveUrl("/**","所有URL",0);
		RequestMappingHandlerMapping requestMappingHandlerMapping = ctx.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		Iterator<Entry<RequestMappingInfo, HandlerMethod>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<RequestMappingInfo, HandlerMethod> entry = it.next();
			RequestMappingInfo mappingInfo = entry.getKey();
			HandlerMethod method = entry.getValue();
			Class<?> cls = method.getBeanType();
			
			ApiInfo clsApiDescription = AnnotatedElementUtils.findMergedAnnotation(cls, ApiInfo.class);
			RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(cls, RequestMapping.class);
			
			if(clsApiDescription != null && requestMapping != null ) {
				
				String urlmc = clsApiDescription.value();
				String[] urls = requestMapping.value();
				for( String url : urls ) {
					saveUrl(url + "/**",urlmc,0);
				}
			}
			
			ApiInfo apiDescription = AnnotatedElementUtils.findMergedAnnotation(method.getMethod(), ApiInfo.class);
			if( apiDescription != null ) {
				
				String urlmc = apiDescription.value();
				PatternsRequestCondition patternsRequestCondition  = mappingInfo.getPatternsCondition();
				Set<String> urls = patternsRequestCondition.getPatterns();
				for( String url :urls) {
					
					saveUrl(url,urlmc,1);
				}
			}
		}
		Map<String,Object> param = new HashMap<>();
		param.put("urlst", 1);
		List<RsUrl> list = urlMapper.queryBeanList(param, null);
		for( RsUrl url: list) {
			roleUrlMapper.deleteRoleUrlByUrl(url.getUrl());
		}
		urlMapper.deleteInvalidUrl();
	}
	
	@Override
	public void saveUrl( String url, String urlmc, int urllx) {
		
		RsUrl dbBean = urlMapper.queryBeanById(url);
		if( dbBean == null ) {
			
			dbBean = new RsUrl();
			dbBean.setUrlmc(urlmc);
			dbBean.setUrl(url);
			dbBean.setUrllx(urllx);
			dbBean.setUrlst(0);
			urlMapper.insertBean(dbBean);
		}else {
			
			dbBean.setUrlmc(urlmc);
			dbBean.setUrllx(urllx);
			dbBean.setUrlst(0);
			urlMapper.updateBean(dbBean);		
		}
	}

	@Override
	public List<RsUrl> queryBeans(Map<String,Object> param, IPage paramPage){
		
		return urlMapper.queryBeanList(param, paramPage);
	}
	
	@Override
	public List<RsUrlRolesModel> queryUrlRolesList(Map<String,Object> param,IPage paramPage){
		
		return urlModelMapper.queryUrlRolesList(param, paramPage);
	}
	
	@Override
	public List<RsUrlRolesModel> queryUrlRolesAllList(){
		Map<String,Object> param = new HashMap<>();
		param.put("orderCause", "ORDER BY URLLX");
		return urlModelMapper.queryUrlRolesList(param, null);
	}
}
