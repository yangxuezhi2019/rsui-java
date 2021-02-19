package org.rs.core.security.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.rs.core.utils.CoreUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

public class RsXssSecurityFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		RsXssRequestWrapper xssRequest = new RsXssRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}
	
	public static class RsXssRequestWrapper extends HttpServletRequestWrapper{

		private final static RsHTMLFilter htmlFilter = new RsHTMLFilter(false);
		private HttpServletRequest originalRequest;
		public RsXssRequestWrapper(HttpServletRequest request) {
			super(request);
			this.originalRequest = request;
		}
		
		private String xssEncode(String input) {
	        return htmlFilter.filter(input);
	    }
		
		@Override
	    public ServletInputStream getInputStream() throws IOException {
	        //非json类型，直接返回
			String contentType = super.getHeader(HttpHeaders.CONTENT_TYPE).toLowerCase();
	        if(!contentType.startsWith("application/json")){
	            return super.getInputStream();
	        }

	        //为空，直接返回
	        String json = StreamUtils.copyToString(super.getInputStream(), Charset.forName("UTF-8"));
	        if (StringUtils.isEmpty(json)) {
	            return super.getInputStream();
	        }

	        //xss过滤
	        json = xssEncode(json);
	        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
	        return new ServletInputStream() {
	            @Override
	            public boolean isFinished() {
	                return true;
	            }

	            @Override
	            public boolean isReady() {
	                return true;
	            }

	            @Override
	            public void setReadListener(ReadListener readListener) {

	            }

	            @Override
	            public int read() throws IOException {
	                return bis.read();
	            }
	        };
	    }

	    @Override
	    public String getParameter(String name) {
	        String value = super.getParameter(xssEncode(name));
	        if (StringUtils.isEmpty(value)) {
	            value = xssEncode(value);
	        }
	        return value;
	    }

	    @Override
	    public String[] getParameterValues(String name) {
	        String[] parameters = super.getParameterValues(name);
	        if (parameters == null || parameters.length == 0) {
	            return null;
	        }

	        for (int i = 0; i < parameters.length; i++) {
	            parameters[i] = xssEncode(parameters[i]);
	        }
	        return parameters;
	    }

	    @Override
	    public Map<String,String[]> getParameterMap() {
	        Map<String,String[]> map = new LinkedHashMap<>();
	        Map<String,String[]> parameters = super.getParameterMap();
	        for (String key : parameters.keySet()) {
	            String[] values = parameters.get(key);
	            for (int i = 0; i < values.length; i++) {
	                values[i] = xssEncode(values[i]);
	            }
	            map.put(key, values);
	        }
	        return map;
	    }
	    @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(xssEncode(name));
	        if (StringUtils.hasText(value)) {
	            value = xssEncode(value);
	        }
	        return value;
	    }
	    
	    public HttpServletRequest getOrgRequest() {
	        return originalRequest;
	    }
	    
	    @Override
		public String getRemoteAddr() {

			return CoreUtils.getIpAddr(originalRequest);
		}

		/**
	             * 获取最原始的request
	    */
	    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
	        if (request instanceof RsXssRequestWrapper) {
	            return ((RsXssRequestWrapper) request).getOrgRequest();
	        }
	        return request;
	    }
	}
}
