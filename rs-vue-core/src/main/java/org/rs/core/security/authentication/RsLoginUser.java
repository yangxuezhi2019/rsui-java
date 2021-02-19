package org.rs.core.security.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.rs.core.beans.model.RsUserAllModel;
import org.rs.core.security.access.RsGrantedAuthority;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class RsLoginUser implements UserDetails,CredentialsContainer{
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	//private static final Logger logger = LoggerFactory.getLogger(RsLoginUser.class);
	private final List<DeptInfo> depts;
	private final String yhbh;
	private final String dlmc;
	private final String yhmc;
	private final String bmbh;
	private final String bmlj;
	private final String bmcj;
	private final String bmmc;
	private final String username;
	private final int sqlx;
	private final Set<RsGrantedAuthority> authorities;
	private String dlmm;
	
	public RsLoginUser(RsUserAllModel rsUser, String dlmc, String dlmm, int sqlx) {
		
		this.yhmc = rsUser.getYhmc();
		this.yhbh = rsUser.getYhbh();
		this.bmbh = rsUser.getBmbh();
		this.bmlj = rsUser.getBmlj();
		this.bmcj = rsUser.getBmcj();
		this.dlmc = rsUser.getDlmc();
		this.bmmc = rsUser.getBmmc();
		this.username = dlmc;
		this.dlmm = dlmm;
		this.sqlx = sqlx;
		
		this.depts = rsUser.getDepts().stream()
				.map(item->{return new DeptInfo(item.getBmbh(),item.getBmmc(),item.getBmlj(),item.getBmcj());})
				.collect(Collectors.toList());
		
		this.depts.sort(new DeptInfoComparator());
		List<RsGrantedAuthority> roleList = rsUser.getRoles().stream()
				.map(role->{return new RsGrantedAuthority(role.getJsbh(),role.getJsnm(),role.getJsmc(),role.getJslx(),role.getJsjb(),role.getJsxh());})
				.collect(Collectors.toList());
		this.authorities = Collections.unmodifiableSet(sortAuthorities(roleList));
	}

	public List<DeptInfo> getDepts() {
		return depts;
	}
	
	public List<DeptInfo> getFilterDepts() {
		
		if(depts.size() == 0 )
			return depts;
		List<DeptInfo> result = new ArrayList<>();
		DeptInfo item = depts.get(0);
		result.add(item);
		for( int i = 1; i < depts.size(); i ++ ) {
			item = depts.get(i);
			if(bmljInDeptInfoList(result,item.getBmlj())) {
				continue;
			}
			result.add(item);
		}
		return result;
	}
	
	public String getBmbh() {
		return bmbh;
	}
	
	public String getBmmc() {
		return bmmc;
	}

	public String getBmlj() {
		return bmlj;
	}

	public String getBmcj() {
		return bmcj;
	}

	public int getSqlx() {
		return sqlx;
	}

	public String getDlmc() {
		return dlmc;
	}

	public String getYhbh() {
		return this.yhbh;
	}
	
	public String getYhmc() {
		return this.yhmc;
	}
	
	@Override
	public Collection<RsGrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return dlmm;
	}

	/**
	 * 用户登录的名称
	 * */
	@Override
	public String getUsername() {
		
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	@Override
	public void eraseCredentials() {
		
		dlmm = null;
	}
	
	public boolean hasRole(String role ) {
		
		for (GrantedAuthority grantedAuthority : authorities) {
			
			if( grantedAuthority.getAuthority().equals(role) ) {
				return true;
			}
		}
		return false;
	}
	
	private static SortedSet<RsGrantedAuthority> sortAuthorities(
			Collection<RsGrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<RsGrantedAuthority> sortedAuthorities = new TreeSet<>(
				new AuthorityComparator());

		for (RsGrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority,
					"GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}
		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<RsGrantedAuthority>,
			Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(RsGrantedAuthority g1, RsGrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before adding it to
			// the set.
			// If the authority is null, it is a custom authority and should precede
			// others.
			if (g2.getAuthority() == null) {
				return -1;
			}
			if (g1.getAuthority() == null) {
				return 1;
			}
			return g1.getJsxh().compareTo(g2.getJsxh());
		}
	}

	@Override
	public int hashCode() {
		return yhbh.hashCode();
	}
	
	private static class DeptInfoComparator implements Comparator<DeptInfo>,Serializable{
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		@Override
		public int compare(DeptInfo o1, DeptInfo o2) {
			
			return o1.getBmjb() - o2.getBmjb();
		}
	}
	
	public static class DeptInfo implements Serializable{
		
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;		
		private final String bmbh;
		private final String bmmc;
		private final String bmlj;
		private final int bmjb;
		
		public DeptInfo(String bmbh,String bmmc,String bmlj,int bmjb) {
			
			this.bmbh = bmbh;
			this.bmmc = bmmc;
			this.bmlj = bmlj;
			this.bmjb = bmjb;
		}
		public String getBmbh() {
			return bmbh;
		}
		public String getBmmc() {
			return bmmc;
		}
		public String getBmlj() {
			return bmlj;
		}
		public int getBmjb() {
			return bmjb;
		}
	}
	
	public static boolean bmljInDeptInfoList(List<DeptInfo> items, String bmlj ){
		
		for( DeptInfo item : items ) {
			if( bmlj.startsWith(item.getBmlj()))
				return true;
		}
		return false;
	}
}
