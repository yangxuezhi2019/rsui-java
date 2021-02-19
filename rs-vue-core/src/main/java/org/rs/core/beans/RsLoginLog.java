package org.rs.core.beans;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsLoginLog {

	private String logid;
	private String userId;
	private String loginName;
	private String userName;
	private String deptId;
	private String deptName;
	private String deptPath;
	private Integer sqly;
	private String ip;
	private Date loginDate;

	/**
	* logid
	*/
	public String getLogid(){
		return this.logid;
	}
	/**
	* logid
	*/
	public void setLogid( String logid ){
		 this.logid=logid;
	}
	/**
	* 用户ID
	*/
	public String getUserId(){
		return this.userId;
	}
	/**
	* 用户ID
	*/
	public void setUserId( String userId ){
		 this.userId=userId;
	}
	/**
	* 登录账号
	*/
	public String getLoginName(){
		return this.loginName;
	}
	/**
	* 登录账号
	*/
	public void setLoginName( String loginName ){
		 this.loginName=loginName;
	}
	/**
	* 用户名称
	*/
	public String getUserName(){
		return this.userName;
	}
	/**
	* 用户名称
	*/
	public void setUserName( String userName ){
		 this.userName=userName;
	}
	/**
	* 部门ID
	*/
	public String getDeptId(){
		return this.deptId;
	}
	/**
	* 部门ID
	*/
	public void setDeptId( String deptId ){
		 this.deptId=deptId;
	}
	/**
	* 部门名称
	*/
	public String getDeptName(){
		return this.deptName;
	}
	/**
	* 部门名称
	*/
	public void setDeptName( String deptName ){
		 this.deptName=deptName;
	}
	/**
	* 部门路径
	*/
	public String getDeptPath(){
		return this.deptPath;
	}
	/**
	* 部门路径
	*/
	public void setDeptPath( String deptPath ){
		 this.deptPath=deptPath;
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
	* 消耗时间
	*/
	public String getIp(){
		return this.ip;
	}
	/**
	* 消耗时间
	*/
	public void setIp( String ip ){
		 this.ip=ip;
	}
	/**
	* 登录时间
	*/
	@JsonSerialize(using = MyJsonDateSerializer.class)
	public Date getLoginDate(){
		return this.loginDate;
	}
	/**
	* 登录时间
	*/
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	public void setLoginDate( Date loginDate ){
		 this.loginDate=loginDate;
	}
}
