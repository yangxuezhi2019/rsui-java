package org.rs.core.beans;


public class RsStrategyInfo {

	private String clbh;
	private String msbh;
	private String tmz;
	private Integer tmzt;
	private Integer tmxh;

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
	* 策略描述编号
	*/
	public String getMsbh(){
		return this.msbh;
	}
	/**
	* 策略描述编号
	*/
	public void setMsbh( String msbh ){
		 this.msbh=msbh;
	}
	/**
	* 策略条目值
	*/
	public String getTmz(){
		return this.tmz;
	}
	/**
	* 策略条目值
	*/
	public void setTmz( String tmz ){
		 this.tmz=tmz;
	}
	/**
	* 策略状态，0-启用，1-锁定，2-删除
	*/
	public Integer getTmzt(){
		return this.tmzt;
	}
	/**
	* 策略状态，0-启用，1-锁定，2-删除
	*/
	public void setTmzt( Integer tmzt ){
		 this.tmzt=tmzt;
	}
	/**
	* 策略排序号
	*/
	public Integer getTmxh(){
		return this.tmxh;
	}
	/**
	* 策略排序号
	*/
	public void setTmxh( Integer tmxh ){
		 this.tmxh=tmxh;
	}
}
