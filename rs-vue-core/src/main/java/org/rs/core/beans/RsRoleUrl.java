package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsRoleUrl {

	@NotNull(message = "角色编号不能为空")
	private String jsbh;
	@NotNull(message = "url不能为空")
	private String url;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人不能为空")
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
	* url
	*/
	public String getUrl(){
		return this.url;
	}
	/**
	* url
	*/
	public void setUrl( String url ){
		 this.url=url;
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
