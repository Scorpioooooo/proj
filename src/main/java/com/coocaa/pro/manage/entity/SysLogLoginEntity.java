package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;import java.util.Date;/** * <br> * <b>功能：</b>登录日志 entity<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-02-28 17:20:35<br> * <b>详细说明：</b>无<br> */public class SysLogLoginEntity implements Serializable {		private static final long serialVersionUID = 1L;		private Integer id;//	private String userName;//   用户名	private Integer userId;//   用户id	private Date loginTime;//   登录时间	private String loginIp;//   用户登录ip	private String userAgent;//   用户浏览器信息	private Integer loginType;//   登录类型	private String remark;//   说明		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="user_name")	public String getUserName() {	    return this.userName;	}	public void setUserName(String userName) {	    this.userName=userName;	}	@Column(name="user_id")	public Integer getUserId() {	    return this.userId;	}	public void setUserId(Integer userId) {	    this.userId=userId;	}	@Column(name="login_time")	public Date getLoginTime() {	    return this.loginTime;	}	public void setLoginTime(Date loginTime) {	    this.loginTime=loginTime;	}	@Column(name="login_ip")	public String getLoginIp() {	    return this.loginIp;	}	public void setLoginIp(String loginIp) {	    this.loginIp=loginIp;	}	@Column(name="user_agent")	public String getUserAgent() {	    return this.userAgent;	}	public void setUserAgent(String userAgent) {	    this.userAgent=userAgent;	}	@Column(name="login_type")	public Integer getLoginType() {	    return this.loginType;	}	public void setLoginType(Integer loginType) {	    this.loginType=loginType;	}	@Column(name="remark")	public String getRemark() {		return remark;	}	public void setRemark(String remark) {		this.remark = remark;	}}