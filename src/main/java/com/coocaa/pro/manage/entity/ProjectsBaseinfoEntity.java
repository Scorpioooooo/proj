package com.coocaa.pro.manage.entity;

import java.io.Serializable;
import com.coocaa.fire.utils.plugin.annotation.Column;

/**
 * <br>
 * <b>功能：</b>基础项目表 Entity<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:45<br>
 * <b>详细说明：</b>无<br>
 */
public class ProjectsBaseinfoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
		private Integer id;//   	private Integer projectId;//   项目ID	private String market;//   市场分析	private String technology;//   技术可行性分析	private String competitor;//   竞品分析	private String policy;//   政策风险分析	private String enclosureDocumentIds;//   附件文件id，以“,”切割		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="project_id")	public Integer getProjectId() {	    return this.projectId;	}	public void setProjectId(Integer projectId) {	    this.projectId=projectId;	}	@Column(name="market")	public String getMarket() {	    return this.market;	}	public void setMarket(String market) {	    this.market=market;	}	@Column(name="technology")	public String getTechnology() {	    return this.technology;	}	public void setTechnology(String technology) {	    this.technology=technology;	}	@Column(name="competitor")	public String getCompetitor() {	    return this.competitor;	}	public void setCompetitor(String competitor) {	    this.competitor=competitor;	}	@Column(name="policy")	public String getPolicy() {	    return this.policy;	}	public void setPolicy(String policy) {	    this.policy=policy;	}	@Column(name="enclosure_document_ids")	public String getEnclosureDocumentIds() {	    return this.enclosureDocumentIds;	}	public void setEnclosureDocumentIds(String enclosureDocumentIds) {	    this.enclosureDocumentIds=enclosureDocumentIds;	}
}
