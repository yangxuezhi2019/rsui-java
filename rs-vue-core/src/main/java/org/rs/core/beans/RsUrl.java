package org.rs.core.beans;


public class RsUrl {

	private String url;
	private String urlmc;
	private Integer urllx;
	private Integer urlst;

	/**
	* url地址
	*/
	public String getUrl(){
		return this.url;
	}
	/**
	* url地址
	*/
	public void setUrl( String url ){
		 this.url=url;
	}
	/**
	* url名称
	*/
	public String getUrlmc(){
		return this.urlmc;
	}
	/**
	* url名称
	*/
	public void setUrlmc( String urlmc ){
		 this.urlmc=urlmc;
	}
	/**
	* url类型，0-类上的url,1-方法上的url
	*/
	public Integer getUrllx(){
		return this.urllx;
	}
	/**
	* url类型，0-类上的url,1-方法上的url
	*/
	public void setUrllx( Integer urllx ){
		 this.urllx=urllx;
	}
	/**
	* url状态，0-正常,1-删除
	*/
	public Integer getUrlst(){
		return this.urlst;
	}
	/**
	* url状态，0-正常,1-删除
	*/
	public void setUrlst( Integer urlst ){
		 this.urlst=urlst;
	}
}
