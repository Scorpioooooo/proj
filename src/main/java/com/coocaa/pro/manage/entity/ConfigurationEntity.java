package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;/** * <br> * <b>功能：</b>配置表 Entity<br> * <b>作者：</b>bean creater<br> * <b>日期：</b>2018-11-29 15:19:10<br> * <b>详细说明：</b>无<br> */public class ConfigurationEntity implements Serializable {		private static final long serialVersionUID = 1L;		private Integer id;//   	private String key;//   	private String value;//   		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="key")	public String getKey() {	    return this.key;	}	public void setKey(String key) {	    this.key=key;	}	@Column(name="value")	public String getValue() {	    return this.value;	}	public void setValue(String value) {	    this.value=value;	}}