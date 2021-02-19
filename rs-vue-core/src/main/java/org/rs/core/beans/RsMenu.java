package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsMenu {

	@NotNull(message = "菜单编号不能为空")
	private String cdbh;
	@NotNull(message = "上级菜单编号不能为空")
	private String pcdbh;
	@NotNull(message = "菜单名称不能为空")
	private String cdmc;
	@NotNull(message = "菜单对应的图标名称不能为空")
	private String icon;
	@NotNull(message = "菜单路径不能为空")
	private String cdlj;
	@NotNull(message = "菜单类型，0-菜单，1-路径不能为空")
	private Integer cdlx;
	@NotNull(message = "是否缓存不能为空")
	private Integer sfhc;
	@NotNull(message = "菜单状态不能为空")
	private Integer cdzt;
	@NotNull(message = "菜单排序号不能为空")
	private Integer cdxh;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人不能为空")
	private String cjrmc;

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
	* 上级菜单编号
	*/
	public String getPcdbh(){
		return this.pcdbh;
	}
	/**
	* 上级菜单编号
	*/
	public void setPcdbh( String pcdbh ){
		 this.pcdbh=pcdbh;
	}
	/**
	* 菜单名称
	*/
	public String getCdmc(){
		return this.cdmc;
	}
	/**
	* 菜单名称
	*/
	public void setCdmc( String cdmc ){
		 this.cdmc=cdmc;
	}
	/**
	* 菜单对应的图标名称
	*/
	public String getIcon(){
		return this.icon;
	}
	/**
	* 菜单对应的图标名称
	*/
	public void setIcon( String icon ){
		 this.icon=icon;
	}
	/**
	* 菜单路径
	*/
	public String getCdlj(){
		return this.cdlj;
	}
	/**
	* 菜单路径
	*/
	public void setCdlj( String cdlj ){
		 this.cdlj=cdlj;
	}
	/**
	* 菜单类型，0-菜单，1-路径
	*/
	public Integer getCdlx(){
		return this.cdlx;
	}
	/**
	* 菜单类型，0-菜单，1-路径
	*/
	public void setCdlx( Integer cdlx ){
		 this.cdlx=cdlx;
	}
	/**
	* 是否缓存：0-缓存，1-不缓存
	*/
	public Integer getSfhc(){
		return this.sfhc;
	}
	/**
	* 是否缓存：0-缓存，1-不缓存
	*/
	public void setSfhc( Integer sfhc ){
		 this.sfhc=sfhc;
	}
	/**
	* 菜单状态：0-正常，1-锁定，2-删除
	*/
	public Integer getCdzt(){
		return this.cdzt;
	}
	/**
	* 菜单状态：0-正常，1-锁定，2-删除
	*/
	public void setCdzt( Integer cdzt ){
		 this.cdzt=cdzt;
	}
	/**
	* 菜单排序号
	*/
	public Integer getCdxh(){
		return this.cdxh;
	}
	/**
	* 菜单排序号
	*/
	public void setCdxh( Integer cdxh ){
		 this.cdxh=cdxh;
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
