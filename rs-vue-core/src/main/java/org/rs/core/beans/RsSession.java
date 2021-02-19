package org.rs.core.beans;

public class RsSession {
	
	private String primary_id;
	private String session_id;
	private Long creation_time;
	private Long last_access_time;
	private Integer max_inactive_interval;
	private Long expiry_time;
	private String ip_addr;
	private String principal_name;
	
	public String getPrimary_id() {
		return primary_id;
	}
	public void setPrimary_id(String primary_id) {
		this.primary_id = primary_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Long getCreation_time() {
		return creation_time;
	}
	public void setCreation_time(Long creation_time) {
		this.creation_time = creation_time;
	}
	public Long getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(Long last_access_time) {
		this.last_access_time = last_access_time;
	}
	public Integer getMax_inactive_interval() {
		return max_inactive_interval;
	}
	public void setMax_inactive_interval(Integer max_inactive_interval) {
		this.max_inactive_interval = max_inactive_interval;
	}
	public Long getExpiry_time() {
		return expiry_time;
	}
	public void setExpiry_time(Long expiry_time) {
		this.expiry_time = expiry_time;
	}
	public String getIp_addr() {
		return ip_addr;
	}
	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}
	public String getPrincipal_name() {
		return principal_name;
	}
	public void setPrincipal_name(String principal_name) {
		this.principal_name = principal_name;
	}
}
