package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsDictInfo {

	@NotNull(message = "数据字典条目主键不能为空")
	private String tmzj;
	@NotNull(message = "数据字典编号不能为空")
	private String zdbh;
	@NotNull(message = "数据字典条目父编号不能为空")
	private String tmpbh;
	@NotNull(message = "数据字典条目编号不能为空")
	private String tmbh;
	@NotNull(message = "数据字典条目名称不能为空")
	private String tmmc;
	@NotNull(message = "数据字典排序号不能为空")
	private Integer tmxh;
	@NotNull(message = "条目状态不能为空")
	private Integer tmzt;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人名称不能为空")
	private String cjrmc;

	/**
	* 数据字典条目主键
	*/
	public String getTmzj(){
		return this.tmzj;
	}
	/**
	* 数据字典条目主键
	*/
	public void setTmzj( String tmzj ){
		 this.tmzj=tmzj;
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
	* 数据字典条目父编号
	*/
	public String getTmpbh(){
		return this.tmpbh;
	}
	/**
	* 数据字典条目父编号
	*/
	public void setTmpbh( String tmpbh ){
		 this.tmpbh=tmpbh;
	}
	/**
	* 数据字典条目编号
	*/
	public String getTmbh(){
		return this.tmbh;
	}
	/**
	* 数据字典条目编号
	*/
	public void setTmbh( String tmbh ){
		 this.tmbh=tmbh;
	}
	/**
	* 数据字典条目名称
	*/
	public String getTmmc(){
		return this.tmmc;
	}
	/**
	* 数据字典条目名称
	*/
	public void setTmmc( String tmmc ){
		 this.tmmc=tmmc;
	}
	/**
	* 数据字典排序号
	*/
	public Integer getTmxh(){
		return this.tmxh;
	}
	/**
	* 数据字典排序号
	*/
	public void setTmxh( Integer tmxh ){
		 this.tmxh=tmxh;
	}
	/**
	* 条目状态：0-正常，1-锁定，2-删除
	*/
	public Integer getTmzt(){
		return this.tmzt;
	}
	/**
	* 条目状态：0-正常，1-锁定，2-删除
	*/
	public void setTmzt( Integer tmzt ){
		 this.tmzt=tmzt;
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
