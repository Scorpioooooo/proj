package com.coocaa.pro.manage.model;/** * <br> * <b>功能：</b>部门信息 Model<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-02-20 15:06:43<br> * <b>详细说明：</b>无<br> */public class SysDeptModel extends BaseModel {		private Integer deptId;//   部门ID	private String deptName;//   部门名称	private String deptTel;//   部门电话	private String deptFax;//   部门传真	private Integer disabled;//   0可用1禁用2删除	private String remark;//   备注	private Integer seq;//   排序	private Integer pid;//   部门父编号		public Integer getDeptId() {	    return this.deptId;	}	public void setDeptId(Integer deptId) {	    this.deptId=deptId;	}	public String getDeptName() {	    return this.deptName;	}	public void setDeptName(String deptName) {	    this.deptName=deptName;	}	public String getDeptTel() {	    return this.deptTel;	}	public void setDeptTel(String deptTel) {	    this.deptTel=deptTel;	}	public String getDeptFax() {	    return this.deptFax;	}	public void setDeptFax(String deptFax) {	    this.deptFax=deptFax;	}	public Integer getDisabled() {	    return this.disabled;	}	public void setDisabled(Integer disabled) {	    this.disabled=disabled;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getSeq() {	    return this.seq;	}	public void setSeq(Integer seq) {	    this.seq=seq;	}	public Integer getPid() {	    return this.pid;	}	public void setPid(Integer pid) {	    this.pid=pid;	}	}