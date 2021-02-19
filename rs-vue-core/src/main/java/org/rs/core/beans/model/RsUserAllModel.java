package org.rs.core.beans.model;

import java.util.List;

import org.rs.core.beans.RsDept;
import org.rs.core.beans.RsRole;

public class RsUserAllModel extends RsUserModel{

	private List<RsDept> depts;
	private List<RsRole> roles;
	
	public List<RsDept> getDepts() {
		return depts;
	}

	public void setDepts(List<RsDept> depts) {
		this.depts = depts;
	}

	public List<RsRole> getRoles() {
		return roles;
	}

	public void setRoles(List<RsRole> roles) {
		this.roles = roles;
	}
}
