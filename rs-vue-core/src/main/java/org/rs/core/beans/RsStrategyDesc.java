package org.rs.core.beans;


public class RsStrategyDesc {

	private String msbh;
	private String msmc;
	private Integer msxh;

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
	* 策略描述名称
	*/
	public String getMsmc(){
		return this.msmc;
	}
	/**
	* 策略描述名称
	*/
	public void setMsmc( String msmc ){
		 this.msmc=msmc;
	}
	/**
	* 策略描述排序号
	*/
	public Integer getMsxh(){
		return this.msxh;
	}
	/**
	* 策略描述排序号
	*/
	public void setMsxh( Integer msxh ){
		 this.msxh=msxh;
	}
}
