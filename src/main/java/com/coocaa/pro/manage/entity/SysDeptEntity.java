package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;/** * <br> * <b>功能：</b>部门信息 entity<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-02-20 15:06:43<br> * <b>详细说明：</b>无<br> */public class SysDeptEntity implements Serializable {		private static final long serialVersionUID = 1L;		private Integer deptId;//   部门ID	private String deptName;//   部门名称	private String deptTel;//   部门电话	private String deptFax;//   部门传真	private Integer disabled;//   0可用1禁用2删除	private String remark;//   备注	private Integer seq;//   排序	private Integer pid;//   部门父编号		@Column(name="dept_id")	public Integer getDeptId() {	    return this.deptId;	}	public void setDeptId(Integer deptId) {	    this.deptId=deptId;	}	@Column(name="dept_name")	public String getDeptName() {	    return this.deptName;	}	public void setDeptName(String deptName) {	    this.deptName=deptName;	}	@Column(name="dept_tel")	public String getDeptTel() {	    return this.deptTel;	}	public void setDeptTel(String deptTel) {	    this.deptTel=deptTel;	}	@Column(name="dept_fax")	public String getDeptFax() {	    return this.deptFax;	}	public void setDeptFax(String deptFax) {	    this.deptFax=deptFax;	}	@Column(name="disabled")	public Integer getDisabled() {	    return this.disabled;	}	public void setDisabled(Integer disabled) {	    this.disabled=disabled;	}	@Column(name="remark")	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	@Column(name="seq")	public Integer getSeq() {	    return this.seq;	}	public void setSeq(Integer seq) {	    this.seq=seq;	}	@Column(name="pid")	public Integer getPid() {	    return this.pid;	}	public void setPid(Integer pid) {	    this.pid=pid;	}}