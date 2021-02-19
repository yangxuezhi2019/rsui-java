package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsDict {

	@NotNull(message = "数据字典主键不能为空")
	private String zdzj;
	@NotNull(message = "数据字典编号不能为空")
	private String zdbh;
	@NotNull(message = "数据字典描述不能为空")
	private String zdmc;
	@NotNull(message = "数据字典类型，0-系统字典，1-用户自定义字典不能为空")
	private Integer zdlx;
	@NotNull(message = "数据字典结构，0-不分层，1-分层树形结构不能为空")
	private Integer zdjg;
	@NotNull(message = "数据字典排序号不能为空")
	private Integer zdxh;
	@NotNull(message = "字典状态不能为空")
	private Integer zdzt;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人名称不能为空")
	private String cjrmc;

	/**
	* 数据字典主键
	*/
	public String getZdzj(){
		return this.zdzj;
	}
	/**
	* 数据字典主键
	*/
	public void setZdzj( String zdzj ){
		 this.zdzj=zdzj;
	}
	/**
	* 数据字典编号
	*/
	public String getZdbh(){
		return this.zdbh;
	}
	/**
	* 数据字典编号
	*/
	public void setZdbh( String zdbh ){
		 this.zdbh=zdbh;
	}
	/**
	* 数据字典描述
	*/
	public String getZdmc(){
		return this.zdmc;
	}
	/**
	* 数据字典描述
	*/
	public void setZdmc( String zdmc ){
		 this.zdmc=zdmc;
	}
	/**
	* 数据字典类型，0-系统字典，1-用户自定义字典
	*/
	public Integer getZdlx(){
		return this.zdlx;
	}
	/**
	* 数据字典类型，0-系统字典，1-用户自定义字典
	*/
	public void setZdlx( Integer zdlx ){
		 this.zdlx=zdlx;
	}
	/**
	* 数据字典结构，0-不分层，1-分层树形结构
	*/
	public Integer getZdjg(){
		return this.zdjg;
	}
	/**
	* 数据字典结构，0-不分层，1-分层树形结构
	*/
	public void setZdjg( Integer zdjg ){
		 this.zdjg=zdjg;
	}
	/**
	* 数据字典排序号
	*/
	public Integer getZdxh(){
		return this.zdxh;
	}
	/**
	* 数据字典排序号
	*/
	public void setZdxh( Integer zdxh ){
		 this.zdxh=zdxh;
	}
	/**
	* 字典状态：0-正常，1-锁定，2-删除
	*/
	public Integer getZdzt(){
		return this.zdzt;
	}
	/**
	* 字典状态：0-正常，1-锁定，2-删除
	*/
	public void setZdzt( Integer zdzt ){
		 this.zdzt=zdzt;
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
