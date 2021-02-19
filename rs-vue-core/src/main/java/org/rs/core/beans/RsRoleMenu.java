package org.rs.core.beans;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsRoleMenu {

	private String jsbh;
	private String cdbh;
	private Date cjsj;
	private String cjrbh;
	private String cjrmc;

	/**
	* 角色编号
	*/
	public String getJsbh(){
		return this.jsbh;
	}
	/**
	* 角色编号
	*/
	public void setJsbh( String jsbh ){
		 this.jsbh=jsbh;
	}
	/**
	* 菜单编号
	*/
	public String getCdbh(){
		return this.cdbh;
	}
	/**
	* 菜单编号
	*/
	public void setCdbh( String cdbh ){
		 this.cdbh=cdbh;
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
