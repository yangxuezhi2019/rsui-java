package org.rs.core.security.access;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class RsGrantedAuthority implements GrantedAuthority{

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private final String jsnm;
	private final String jsbh;
	private final String jsmc;
	private final Integer jslx;
	private final Integer jsjb;
	private final Integer jsxh;
	
	public RsGrantedAuthority(String jsbh,String jsnm,String jsmc,Integer jslx,Integer jsjb,Integer jsxh) {
		
		this.jsnm = jsnm;
		this.jsbh = jsbh;
		this.jsmc = jsmc;
		this.jslx = jslx;
		this.jsjb = jsjb;
		this.jsxh = jsxh;
	}
	
	@Override
	public String getAuthority() {
		
		return jsnm;
	}

	public String getJsbh() {
		return jsbh;
	}

	public String getJsmc() {
		return jsmc;
	}

	public Integer getJslx() {
		return jslx;
	}

	public Integer getJsjb() {
		return jsjb;
	}

	public Integer getJsxh() {
		return jsxh;
	}

	@Override
	public int hashCode() {
		
		return jsbh.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if( obj instanceof RsGrantedAuthority) {
			
			RsGrantedAuthority tmp = (RsGrantedAuthority)obj;
			return tmp.jsbh.equals(jsbh);
		}
		return false;
	}

	@Override
	public String toString() {
		
		return jsnm;
	}
}
