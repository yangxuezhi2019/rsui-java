package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsRole {

	@NotNull(message = "角色编号不能为空")
	private String jsbh;
	@NotNull(message = "角色名字不能为空")
	private String jsnm;
	@NotNull(message = "角色名称不能为空")
	private String jsmc;
	@NotNull(message = "角色类型不能为空")
	private Integer jslx;
	@NotNull(message = "角色级别不能为空")
	private Integer jsjb;
	@NotNull(message = "角色序号，用来排序不能为空")
	private Integer jsxh;
	@NotNull(message = "角色状态不能为空")
	private Integer jszt;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人名称不能为空")
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
	* 角色名字
	*/
	public String getJsnm(){
		return this.jsnm;
	}
	/**
	* 角色名字
	*/
	public void setJsnm( String jsnm ){
		 this.jsnm=jsnm;
	}
	/**
	* 角色名称
	*/
	public String getJsmc(){
		return this.jsmc;
	}
	/**
	* 角色名称
	*/
	public void setJsmc( String jsmc ){
		 this.jsmc=jsmc;
	}
	/**
	* 角色类型，0-管理角色，其他-自定义角色
	*/
	public Integer getJslx(){
		return this.jslx;
	}
	/**
	* 角色类型，0-管理角色，其他-自定义角色
	*/
	public void setJslx( Integer jslx ){
		 this.jslx=jslx;
	}
	/**
	* 角色级别
	*/
	public Integer getJsjb(){
		return this.jsjb;
	}
	/**
	* 角色级别
	*/
	public void setJsjb( Integer jsjb ){
		 this.jsjb=jsjb;
	}
	/**
	* 角色序号，用来排序
	*/
	public Integer getJsxh(){
		return this.jsxh;
	}
	/**
	* 角色序号，用来排序
	*/
	public void setJsxh( Integer jsxh ){
		 this.jsxh=jsxh;
	}
	/**
	* 角色状态：0-正常，1-锁定，2-删除
	*/
	public Integer getJszt(){
		return this.jszt;
	}
	/**
	* 角色状态：0-正常，1-锁定，2-删除
	*/
	public void setJszt( Integer jszt ){
		 this.jszt=jszt;
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
