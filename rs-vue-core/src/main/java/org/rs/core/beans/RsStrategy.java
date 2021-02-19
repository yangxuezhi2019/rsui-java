package org.rs.core.beans;


public class RsStrategy {

	private String clbh;
	private String clmc;
	private Integer clzt;
	private Integer clxh;

	/**
	* 策略编号
	*/
	public String getClbh(){
		return this.clbh;
	}
	/**
	* 策略编号
	*/
	public void setClbh( String clbh ){
		 this.clbh=clbh;
	}
	/**
	* 策略名称
	*/
	public String getClmc(){
		return this.clmc;
	}
	/**
	* 策略名称
	*/
	public void setClmc( String clmc ){
		 this.clmc=clmc;
	}
	/**
	* 策略状态，0-启用，1-关闭
	*/
	public Integer getClzt(){
		return this.clzt;
	}
	/**
	* 策略状态，0-启用，1-关闭
	*/
	public void setClzt( Integer clzt ){
		 this.clzt=clzt;
	}
	/**
	* 策略排序号
	*/
	public Integer getClxh(){
		return this.clxh;
	}
	/**
	* 策略排序号
	*/
	public void setClxh( Integer clxh ){
		 this.clxh=clxh;
	}
}
