package org.rs.core.beans;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsDeptUser {

	private String bmbh;
	private String yhbh;
	private Date cjsj;
	private String cjrbh;
	private String cjrmc;

	/**
	* 部门编号
	*/
	public String getBmbh(){
		return this.bmbh;
	}
	/**
	* 部门编号
	*/
	public void setBmbh( String bmbh ){
		 this.bmbh=bmbh;
	}
	/**
	* 用户编号
	*/
	public String getYhbh(){
		return this.yhbh;
	}
	/**
	* 用户编号
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
	* 创建人
	*/
	public String getCjrmc(){
		return this.cjrmc;
	}
	/**
	* 创建人
	*/
	public void setCjrmc( String cjrmc ){
		 this.cjrmc=cjrmc;
	}
}
