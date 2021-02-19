package org.rs.core.beans.model;

import java.util.List;

import org.rs.core.beans.RsRole;
import org.rs.core.beans.RsUrl;

public class RsUrlRolesModel extends RsUrl{

	private List<RsRole> roles;

	public List<RsRole> getRoles() {
		return roles;
	}

	public void setRoles(List<RsRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "[URL=" + getUrl() + ", URLLX=" + getUrllx() + "]";
	}
}
