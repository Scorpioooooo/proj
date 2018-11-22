package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;import java.util.Date;/** * <br> * <b>功能：</b>操作日志 entity<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-06-26 09:06:55<br> * <b>详细说明：</b>无<br> */public class SysLogOpEntity implements Serializable {		private static final long serialVersionUID = 1L;		private Integer id;//   ID	private Date createTime;//   操作时间	private Integer elapsedTime;//   耗用时间	private String execSql;//   执行的语句	private Integer userId;//   执行人id	private String userName;//   执行人名称	private String execType;//   动作,delete update insert	private String execTable;//   操作表	@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="create_time")	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	@Column(name="elapsed_time")	public Integer getElapsedTime() {	    return this.elapsedTime;	}	public void setElapsedTime(Integer elapsedTime) {	    this.elapsedTime=elapsedTime;	}	@Column(name="exec_sql")	public String getExecSql() {	    return this.execSql;	}	public void setExecSql(String execSql) {	    this.execSql=execSql;	}	@Column(name="user_id")	public Integer getUserId() {	    return this.userId;	}	public void setUserId(Integer userId) {	    this.userId=userId;	}	@Column(name="user_name")	public String getUserName() {	    return this.userName;	}	public void setUserName(String userName) {	    this.userName=userName;	}	@Column(name="exec_type")	public String getExecType() {	    return this.execType;	}	public void setExecType(String execType) {	    this.execType=execType;	}	@Column(name="exec_table")	public String getExecTable() {	    return this.execTable;	}	public void setExecTable(String execTable) {	    this.execTable=execTable;	}}