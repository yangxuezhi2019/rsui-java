package org.rs.core.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rs.core.page.DbPage;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CoreUtils {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		
		String header = request.getHeader("X-Requested-With");
		return !StringUtils.isEmpty(header) && "XMLHttpRequest".equals(header);
	}
	
	public static HttpServletRequest getHttpServletRequest() {
		
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}
	
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	
        }
        
        //使用代理，则获取第一个IP地址
        if( !StringUtils.isEmpty(ip) ) {
        	String[] ipArray = StringUtils.delimitedListToStringArray(ip, ",");
        	return ipArray[0];
		}
        return "";
    }
	
	public static boolean isJsonRequest(HttpServletRequest request) {
		
		String header = request.getHeader("Content-Type");
		return !StringUtils.isEmpty(header) && header.startsWith("application/json");
	}
	
	public static DbPage constructPage(Map<String, Object> param) {
		
		DbPage page = null;
		Object pageSize = param.get("pageSize");
		Object curPage = param.get("page");
		if( pageSize != null && curPage != null ) {
			
			page = new DbPage((Integer)curPage,(Integer)pageSize);
		}		
		return page;
	}
	
	public static void putPageInfo(DbPage page,Map<String, Object> result) {
		
		if( page != null ) {
			
			result.put("totalCount", page.getTotalCount());
			result.put("page", page.getCurrentPage());
			result.put("pageSize", page.getPageSize());
		}
	}
}
