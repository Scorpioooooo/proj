package com.coocaa.pro.manage.model;

/**
 * <br>
 * <b>功能：</b>项目评价表 Model<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:45<br>
 * <b>详细说明：</b>无<br>
 */
public class ProjectsEvaluationModel extends BaseModel {
		private Integer id;//   id	private Integer projId;//   项目id	private Integer level;//   项目评级	private Integer scoreFinal;//   项目打分	private Integer scorePlan;//   计划，策划评分	private Integer scoreTest;//   测试评分	private Integer scoreDevelope;//   开发评分		public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getProjId() {	    return this.projId;	}	public void setProjId(Integer projId) {	    this.projId=projId;	}	public Integer getLevel() {	    return this.level;	}	public void setLevel(Integer level) {	    this.level=level;	}	public Integer getScoreFinal() {	    return this.scoreFinal;	}	public void setScoreFinal(Integer scoreFinal) {	    this.scoreFinal=scoreFinal;	}	public Integer getScorePlan() {	    return this.scorePlan;	}	public void setScorePlan(Integer scorePlan) {	    this.scorePlan=scorePlan;	}	public Integer getScoreTest() {	    return this.scoreTest;	}	public void setScoreTest(Integer scoreTest) {	    this.scoreTest=scoreTest;	}	public Integer getScoreDevelope() {	    return this.scoreDevelope;	}	public void setScoreDevelope(Integer scoreDevelope) {	    this.scoreDevelope=scoreDevelope;	}
	
}
