package com.coocaa.pro.manage.model;

/**
 * <br>
 * <b>功能：</b>基础项目表 Model<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:45<br>
 * <b>详细说明：</b>无<br>
 */
public class ProjectsBaseinfoModel extends BaseModel {
		private Integer id;//   	private Integer projectId;//   项目ID	private String market;//   市场分析	private String technology;//   技术可行性分析	private String competitor;//   竞品分析	private String policy;//   政策风险分析	private String enclosureDocumentIds;//   附件文件id，以“,”切割		public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getProjectId() {	    return this.projectId;	}	public void setProjectId(Integer projectId) {	    this.projectId=projectId;	}	public String getMarket() {	    return this.market;	}	public void setMarket(String market) {	    this.market=market;	}	public String getTechnology() {	    return this.technology;	}	public void setTechnology(String technology) {	    this.technology=technology;	}	public String getCompetitor() {	    return this.competitor;	}	public void setCompetitor(String competitor) {	    this.competitor=competitor;	}	public String getPolicy() {	    return this.policy;	}	public void setPolicy(String policy) {	    this.policy=policy;	}	public String getEnclosureDocumentIds() {	    return this.enclosureDocumentIds;	}	public void setEnclosureDocumentIds(String enclosureDocumentIds) {	    this.enclosureDocumentIds=enclosureDocumentIds;	}
	
}
