package org.rs.core.beans;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rs.core.utils.MyJsonDateSerializer;
import org.rs.core.utils.MyJsonDateDeserializer;

public class RsUser {

	@NotNull(message = "用户编号不能为空")
	private String yhbh;
	@NotNull(message = "登录名称不能为空")
	private String dlmc;
	@NotNull(message = "用户名称不能为空")
	private String yhmc;
	@NotNull(message = "部门编号不能为空")
	private String bmbh;
	private String phone;
	private String email;
	private String address;
	@NotNull(message = "用户是否复核，0-正常，1-待复核不能为空")
	private Integer sffh;
	@NotNull(message = "用户类型，0-默认用户，1-普通用户，其它值为自定义用户类型不能为空")
	private Integer yhlx;
	@NotNull(message = "用户状态，0-正常，1-锁定，2-删除不能为空")
	private Integer yhzt;
	@NotNull(message = "记录登录失败的次数，登录成功后清零不能为空")
	private Integer dlcs;
	@NotNull(message = "密码超期检测,0-不检测，1-检测不能为空")
	private Integer cqjc;
	@NotNull(message = "创建时间不能为空")
	private Date cjsj;
	@NotNull(message = "创建人编号不能为空")
	private String cjrbh;
	@NotNull(message = "创建人名称不能为空")
	private String cjrmc;

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
	* 登录名称
	*/
	public String getDlmc(){
		return this.dlmc;
	}
	/**
	* 登录名称
	*/
	public void setDlmc( String dlmc ){
		 this.dlmc=dlmc;
	}
	/**
	* 用户名称
	*/
	public String getYhmc(){
		return this.yhmc;
	}
	/**
	* 用户名称
	*/
	public void setYhmc( String yhmc ){
		 this.yhmc=yhmc;
	}
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
	* 手机号码
	*/
	public String getPhone(){
		return this.phone;
	}
	/**
	* 手机号码
	*/
	public void setPhone( String phone ){
		 this.phone=phone;
	}
	/**
	* 邮件地址
	*/
	public String getEmail(){
		return this.email;
	}
	/**
	* 邮件地址
	*/
	public void setEmail( String email ){
		 this.email=email;
	}
	/**
	* 用户地址
	*/
	public String getAddress(){
		return this.address;
	}
	/**
	* 用户地址
	*/
	public void setAddress( String address ){
		 this.address=address;
	}
	/**
	* 用户是否复核，0-正常，1-待复核
	*/
	public Integer getSffh(){
		return this.sffh;
	}
	/**
	* 用户是否复核，0-正常，1-待复核
	*/
	public void setSffh( Integer sffh ){
		 this.sffh=sffh;
	}
	/**
	* 用户类型，0-默认用户，1-普通用户，其它值为自定义用户类型
	*/
	public Integer getYhlx(){
		return this.yhlx;
	}
	/**
	* 用户类型，0-默认用户，1-普通用户，其它值为自定义用户类型
	*/
	public void setYhlx( Integer yhlx ){
		 this.yhlx=yhlx;
	}
	/**
	* 用户状态，0-正常，1-锁定，2-删除
	*/
	public Integer getYhzt(){
		return this.yhzt;
	}
	/**
	* 用户状态，0-正常，1-锁定，2-删除
	*/
	public void setYhzt( Integer yhzt ){
		 this.yhzt=yhzt;
	}
	/**
	* 记录登录失败的次数，登录成功后清零
	*/
	public Integer getDlcs(){
		return this.dlcs;
	}
	/**
	* 记录登录失败的次数，登录成功后清零
	*/
	public void setDlcs( Integer dlcs ){
		 this.dlcs=dlcs;
	}
	/**
	* 密码超期检测,0-不检测，1-检测
	*/
	public Integer getCqjc(){
		return this.cqjc;
	}
	/**
	* 密码超期检测,0-不检测，1-检测
	*/
	public void setCqjc( Integer cqjc ){
		 this.cqjc=cqjc;
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
