package com.coocaa.pro.manage.entity;

import java.io.Serializable;
import com.coocaa.fire.utils.plugin.annotation.Column;

/**
 * <br>
 * <b>功能：</b>项目表 Entity<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:45<br>
 * <b>详细说明：</b>无<br>
 */
public class ProjectsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
		private Integer id;//   id	private Integer projId;//   项目id	private String projName;//   项目名称	private Integer projInitiatorId;//   项目发起人id	private String projIncludeDepartmentIds;//   项目参与部门id列表，以“,”分割	private Integer projManagerId;//   项目总负责人	private null projStatus;//   项目状态	private null projStage;//   项目阶段	private Integer projCompletionDegree;//   项目完成度	private null projGrade;//   项目重要等级	private Integer projRisk;//   项目风险等级	private String riskIds;//   风险id	private String milepostIds;//   	private Date projBeginTime;//   项目起始时间	private Date projEndTime;//   项目预计结束时间	private Date projRealBeginTime;//   项目实际起始时间	private Date projRealEndTime;//   项目实际结束时间	private Date createTime;//   本条信息创建时间	private Date updateTime;//   本条信息更新时间		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="proj_id")	public Integer getProjId() {	    return this.projId;	}	public void setProjId(Integer projId) {	    this.projId=projId;	}	@Column(name="proj_name")	public String getProjName() {	    return this.projName;	}	public void setProjName(String projName) {	    this.projName=projName;	}	@Column(name="proj_initiator_id")	public Integer getProjInitiatorId() {	    return this.projInitiatorId;	}	public void setProjInitiatorId(Integer projInitiatorId) {	    this.projInitiatorId=projInitiatorId;	}	@Column(name="proj_include_department_ids")	public String getProjIncludeDepartmentIds() {	    return this.projIncludeDepartmentIds;	}	public void setProjIncludeDepartmentIds(String projIncludeDepartmentIds) {	    this.projIncludeDepartmentIds=projIncludeDepartmentIds;	}	@Column(name="proj_manager_id")	public Integer getProjManagerId() {	    return this.projManagerId;	}	public void setProjManagerId(Integer projManagerId) {	    this.projManagerId=projManagerId;	}	@Column(name="proj_status")	public null getProjStatus() {	    return this.projStatus;	}	public void setProjStatus(null projStatus) {	    this.projStatus=projStatus;	}	@Column(name="proj_stage")	public null getProjStage() {	    return this.projStage;	}	public void setProjStage(null projStage) {	    this.projStage=projStage;	}	@Column(name="proj_completion_degree")	public Integer getProjCompletionDegree() {	    return this.projCompletionDegree;	}	public void setProjCompletionDegree(Integer projCompletionDegree) {	    this.projCompletionDegree=projCompletionDegree;	}	@Column(name="proj_grade")	public null getProjGrade() {	    return this.projGrade;	}	public void setProjGrade(null projGrade) {	    this.projGrade=projGrade;	}	@Column(name="proj_risk")	public Integer getProjRisk() {	    return this.projRisk;	}	public void setProjRisk(Integer projRisk) {	    this.projRisk=projRisk;	}	@Column(name="risk_ids")	public String getRiskIds() {	    return this.riskIds;	}	public void setRiskIds(String riskIds) {	    this.riskIds=riskIds;	}	@Column(name="milepost_ids")	public String getMilepostIds() {	    return this.milepostIds;	}	public void setMilepostIds(String milepostIds) {	    this.milepostIds=milepostIds;	}	@Column(name="proj_begin_time")	public Date getProjBeginTime() {	    return this.projBeginTime;	}	public void setProjBeginTime(Date projBeginTime) {	    this.projBeginTime=projBeginTime;	}	@Column(name="proj_end_time")	public Date getProjEndTime() {	    return this.projEndTime;	}	public void setProjEndTime(Date projEndTime) {	    this.projEndTime=projEndTime;	}	@Column(name="proj_real_begin_time")	public Date getProjRealBeginTime() {	    return this.projRealBeginTime;	}	public void setProjRealBeginTime(Date projRealBeginTime) {	    this.projRealBeginTime=projRealBeginTime;	}	@Column(name="proj_real_end_time")	public Date getProjRealEndTime() {	    return this.projRealEndTime;	}	public void setProjRealEndTime(Date projRealEndTime) {	    this.projRealEndTime=projRealEndTime;	}	@Column(name="create_time")	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	@Column(name="update_time")	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}
}
