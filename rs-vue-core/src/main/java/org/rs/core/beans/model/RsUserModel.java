package org.rs.core.beans.model;

import org.rs.core.beans.RsUser;

public class RsUserModel extends RsUser{

	private String bmdm;
	private String bmmc;
	private String bmlj;
	private String bmcj;
	
	public String getBmdm() {
		return bmdm;
	}
	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getBmlj() {
		return bmlj;
	}
	public void setBmlj(String bmlj) {
		this.bmlj = bmlj;
	}
	public String getBmcj() {
		return bmcj;
	}
	public void setBmcj(String bmcj) {
		this.bmcj = bmcj;
	}
}
