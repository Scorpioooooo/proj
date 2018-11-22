package com.coocaa.pro.manage.utils.captcha;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CaptchaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6899449808870184566L;
	
	private String username;
	private String token;
	private String uuid;
	private String captcha;
	private Date createTime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 校验验证码是否匹配
	 * @param captcha
	 * @return true->正确 false->错误
	 */
	public boolean check(String captcha){
		return this.captcha.equals(captcha);
	}
	
	/**
	 * 验证码是否过期
	 * @return true->过期  false->未过期
	 */
	public boolean expired(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		if(calendar.getTime().getTime()<this.createTime.getTime()){
			return false;
		}
		return true;
	}

}
