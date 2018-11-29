package com.coocaa.pro.manage.model;
import java.util.Date;
/**
 * <br>
 * <b>功能：</b>风险表 Model<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-29 15:19:11<br>
 * <b>详细说明：</b>无<br>
 */
public class RisksModel extends BaseModel {
		private Integer id;//   	private Integer riskId;//   	private Integer orderby;//   排序	private String riskDiscription;//   风险描述	private String riskSolution;//   风险解决方案	private Integer riskIsresolved;//   是否已经解决	private Date resolveTime;//   风险解决时间	private Date createTime;//   	private Date updateTime;//   		public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getRiskId() {	    return this.riskId;	}	public void setRiskId(Integer riskId) {	    this.riskId=riskId;	}	public Integer getOrderby() {	    return this.orderby;	}	public void setOrderby(Integer orderby) {	    this.orderby=orderby;	}	public String getRiskDiscription() {	    return this.riskDiscription;	}	public void setRiskDiscription(String riskDiscription) {	    this.riskDiscription=riskDiscription;	}	public String getRiskSolution() {	    return this.riskSolution;	}	public void setRiskSolution(String riskSolution) {	    this.riskSolution=riskSolution;	}	public Integer getRiskIsresolved() {	    return this.riskIsresolved;	}	public void setRiskIsresolved(Integer riskIsresolved) {	    this.riskIsresolved=riskIsresolved;	}	public Date getResolveTime() {	    return this.resolveTime;	}	public void setResolveTime(Date resolveTime) {	    this.resolveTime=resolveTime;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}
	
}
