package org.rs.core.beans;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsAccessLog {

	private String logid;
	private String operation;
	private String methodName;
	private String parameters;
	private Integer milliseconds;
	private String ip;
	private Date accessDate;
	private String userId;
	private String userName;
	private String deptId;
	private String deptName;
	private String deptPath;

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
	* 方法
	*/
	public String getOperation(){
		return this.operation;
	}
	/**
	* 方法
	*/
	public void setOperation( String operation ){
		 this.operation=operation;
	}
	/**
	* 方法
	*/
	public String getMethodName(){
		return this.methodName;
	}
	/**
	* 方法
	*/
	public void setMethodName( String methodName ){
		 this.methodName=methodName;
	}
	/**
	* 参数
	*/
	public String getParameters(){
		return this.parameters;
	}
	/**
	* 参数
	*/
	public void setParameters( String parameters ){
		 this.parameters=parameters;
	}
	/**
	* 消耗时间
	*/
	public Integer getMilliseconds(){
		return this.milliseconds;
	}
	/**
	* 消耗时间
	*/
	public void setMilliseconds( Integer milliseconds ){
		 this.milliseconds=milliseconds;
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
	* 访问时间
	*/
	@JsonSerialize(using = MyJsonDateSerializer.class)
	public Date getAccessDate(){
		return this.accessDate;
	}
	/**
	* 访问时间
	*/
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	public void setAccessDate( Date accessDate ){
		 this.accessDate=accessDate;
	}
	/**
	* 访问人编号
	*/
	public String getUserId(){
		return this.userId;
	}
	/**
	* 访问人编号
	*/
	public void setUserId( String userId ){
		 this.userId=userId;
	}
	/**
	* 访问人名称
	*/
	public String getUserName(){
		return this.userName;
	}
	/**
	* 访问人名称
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
}
