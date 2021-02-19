package org.rs.core.beans;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsUserAuth {

	private String sqbh;
	private Integer sqly;
	private String dlmc;
	private String yhbh;
	private Date cjsj;
	private String cjrbh;
	private String cjrmc;

	/**
	* 授权编号
	*/
	public String getSqbh(){
		return this.sqbh;
	}
	/**
	* 授权编号
	*/
	public void setSqbh( String sqbh ){
		 this.sqbh=sqbh;
	}
	/**
	* 授权类型，0-登录名，1-手机号，2-邮箱，其它值待实现
	*/
	public Integer getSqly(){
		return this.sqly;
	}
	/**
	* 授权类型，0-登录名，1-手机号，2-邮箱，其它值待实现
	*/
	public void setSqly( Integer sqly ){
		 this.sqly=sqly;
	}
	/**
	* 登录名称或者第三方登录的账号
	*/
	public String getDlmc(){
		return this.dlmc;
	}
	/**
	* 登录名称或者第三方登录的账号
	*/
	public void setDlmc( String dlmc ){
		 this.dlmc=dlmc;
	}
	/**
	* 授权对应的用户编号
	*/
	public String getYhbh(){
		return this.yhbh;
	}
	/**
	* 授权对应的用户编号
	*/
	public void setYhbh( String yhbh ){
		 this.yhbh=yhbh;
	}
	/**
	* 创建时间
	*/
	@JsonSerialize(using = MyJsonDateSerializer.class)
	public Date getCjsj(){
		return this.cjsj;
	}
	/**
	* 创建时间
	*/
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	public void setCjsj( Date cjsj ){
		 this.cjsj=cjsj;
	}
	/**
	* 创建人编号
	*/
	public String getCjrbh(){
		return this.cjrbh;
	}
	/**
	* 创建人编号
	*/
	public void setCjrbh( String cjrbh ){
		 this.cjrbh=cjrbh;
	}
	/**
	* 创建人名称
	*/
	public String getCjrmc(){
		return this.cjrmc;
	}
	/**
	* 创建人名称
	*/
	public void setCjrmc( String cjrmc ){
		 this.cjrmc=cjrmc;
	}
}
