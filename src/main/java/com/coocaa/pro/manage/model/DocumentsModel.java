package com.coocaa.pro.manage.model;

/**
 * <br>
 * <b>功能：</b>文档管理 Model<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:43<br>
 * <b>详细说明：</b>无<br>
 */
public class DocumentsModel extends BaseModel {
		private Integer id;//   	private Integer documentId;//   文档id	private String documentName;//   文档名称	private String documentFolder;//   文档保存路径	private Integer documentSize;//   文件大小	private String documentMd5;//   文档md5值	private Integer belongProjectId;//   所属项目	private Integer belongTaskId;//   归属任务ID，可为空	private Integer uploadPersonId;//   上传人员	private Date createTime;//   文档创建时间	private Date updateTime;//   文档修改时间		private ProjectsEntity projects;//   		public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getDocumentId() {	    return this.documentId;	}	public void setDocumentId(Integer documentId) {	    this.documentId=documentId;	}	public String getDocumentName() {	    return this.documentName;	}	public void setDocumentName(String documentName) {	    this.documentName=documentName;	}	public String getDocumentFolder() {	    return this.documentFolder;	}	public void setDocumentFolder(String documentFolder) {	    this.documentFolder=documentFolder;	}	public Integer getDocumentSize() {	    return this.documentSize;	}	public void setDocumentSize(Integer documentSize) {	    this.documentSize=documentSize;	}	public String getDocumentMd5() {	    return this.documentMd5;	}	public void setDocumentMd5(String documentMd5) {	    this.documentMd5=documentMd5;	}	public Integer getBelongProjectId() {	    return this.belongProjectId;	}	public void setBelongProjectId(Integer belongProjectId) {	    this.belongProjectId=belongProjectId;	}	public Integer getBelongTaskId() {	    return this.belongTaskId;	}	public void setBelongTaskId(Integer belongTaskId) {	    this.belongTaskId=belongTaskId;	}	public Integer getUploadPersonId() {	    return this.uploadPersonId;	}	public void setUploadPersonId(Integer uploadPersonId) {	    this.uploadPersonId=uploadPersonId;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}	public ProjectsEntity getProjects() {	    return this.projects;	}	public void setProjects(ProjectsEntity projects) {	    this.projects=projects;	}
	
}
