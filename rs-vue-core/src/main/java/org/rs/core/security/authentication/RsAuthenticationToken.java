package org.rs.core.security.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

public class RsAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	public RsAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	public RsAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal,credentials,authorities);
	}
	/**
	 * 返回用户编号，不是用户登录的名称
	 * */
	@Override
	public String getName() {
		
		if (this.getPrincipal() instanceof RsLoginUser) {
			return ((RsLoginUser) this.getPrincipal()).getYhbh();
		}
		return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
	}
	
	static public RsAuthenticationToken createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		
		RsAuthenticationToken result = new RsAuthenticationToken(
				principal, authentication.getCredentials(),	user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}
}
