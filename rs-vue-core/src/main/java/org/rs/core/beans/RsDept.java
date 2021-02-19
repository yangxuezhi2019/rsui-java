package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsDept {

	@NotNull(message = "部门编号不能为空")
	private String bmbh;
	@NotNull(message = "部门组织代码不能为空")
	private String bmdm;
	@NotNull(message = "部门名称不能为空")
	private String bmmc;
	@NotNull(message = "上级部门编号，root为根部门编号不能为空")
	private String bmpbh;
	@NotNull(message = "状态不能为空")
	private Integer bmzt;
	@NotNull(message = "部门路径，逗号分割的部门编号不能为空")
	private String bmlj;
	@NotNull(message = "部门层级，从零开始，逐渐递加不能为空")
	private Integer bmcj;
	@NotNull(message = "部门排序号不能为空")
	private Integer bmxh;
	@NotNull(message = "部门是否复核，0-正常，1-待复核不能为空")
	private Integer bmfh;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人名称不能为空")
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
	* 部门组织代码
	*/
	public String getBmdm(){
		return this.bmdm;
	}
	/**
	* 部门组织代码
	*/
	public void setBmdm( String bmdm ){
		 this.bmdm=bmdm;
	}
	/**
	* 部门名称
	*/
	public String getBmmc(){
		return this.bmmc;
	}
	/**
	* 部门名称
	*/
	public void setBmmc( String bmmc ){
		 this.bmmc=bmmc;
	}
	/**
	* 上级部门编号，root为根部门编号
	*/
	public String getBmpbh(){
		return this.bmpbh;
	}
	/**
	* 上级部门编号，root为根部门编号
	*/
	public void setBmpbh( String bmpbh ){
		 this.bmpbh=bmpbh;
	}
	/**
	* 部门状态，0-正常，1-锁定，2-注销
	*/
	public Integer getBmzt(){
		return this.bmzt;
	}
	/**
	* 部门状态，0-正常，1-锁定，2-注销
	*/
	public void setBmzt( Integer bmzt ){
		 this.bmzt=bmzt;
	}
	/**
	* 部门路径，逗号分割的部门编号
	*/
	public String getBmlj(){
		return this.bmlj;
	}
	/**
	* 部门路径，逗号分割的部门编号
	*/
	public void setBmlj( String bmlj ){
		 this.bmlj=bmlj;
	}
	/**
	* 部门层级，从零开始，逐渐递加
	*/
	public Integer getBmcj(){
		return this.bmcj;
	}
	/**
	* 部门层级，从零开始，逐渐递加
	*/
	public void setBmcj( Integer bmcj ){
		 this.bmcj=bmcj;
	}
	/**
	* 部门排序号
	*/
	public Integer getBmxh(){
		return this.bmxh;
	}
	/**
	* 部门排序号
	*/
	public void setBmxh( Integer bmxh ){
		 this.bmxh=bmxh;
	}
	/**
	* 部门是否复核，0-正常，1-待复核
	*/
	public Integer getBmfh(){
		return this.bmfh;
	}
	/**
	* 部门是否复核，0-正常，1-待复核
	*/
	public void setBmfh( Integer bmfh ){
		 this.bmfh=bmfh;
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
