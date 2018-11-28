package com.coocaa.pro.manage.entity;

import java.io.Serializable;
import com.coocaa.fire.utils.plugin.annotation.Column;

/**
 * <br>
 * <b>功能：</b>文档管理 Entity<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:43<br>
 * <b>详细说明：</b>无<br>
 */
public class DocumentsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
		private Integer id;//   	private Integer documentId;//   文档id	private String documentName;//   文档名称	private String documentFolder;//   文档保存路径	private Integer documentSize;//   文件大小	private String documentMd5;//   文档md5值	private Integer belongProjectId;//   所属项目	private Integer belongTaskId;//   归属任务ID，可为空	private Integer uploadPersonId;//   上传人员	private Date createTime;//   文档创建时间	private Date updateTime;//   文档修改时间		private ProjectsEntity projects;//   		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="document_id")	public Integer getDocumentId() {	    return this.documentId;	}	public void setDocumentId(Integer documentId) {	    this.documentId=documentId;	}	@Column(name="document_name")	public String getDocumentName() {	    return this.documentName;	}	public void setDocumentName(String documentName) {	    this.documentName=documentName;	}	@Column(name="document_folder")	public String getDocumentFolder() {	    return this.documentFolder;	}	public void setDocumentFolder(String documentFolder) {	    this.documentFolder=documentFolder;	}	@Column(name="document_size")	public Integer getDocumentSize() {	    return this.documentSize;	}	public void setDocumentSize(Integer documentSize) {	    this.documentSize=documentSize;	}	@Column(name="document_md5")	public String getDocumentMd5() {	    return this.documentMd5;	}	public void setDocumentMd5(String documentMd5) {	    this.documentMd5=documentMd5;	}	@Column(name="belong_project_id")	public Integer getBelongProjectId() {	    return this.belongProjectId;	}	public void setBelongProjectId(Integer belongProjectId) {	    this.belongProjectId=belongProjectId;	}	@Column(name="belong_task_id")	public Integer getBelongTaskId() {	    return this.belongTaskId;	}	public void setBelongTaskId(Integer belongTaskId) {	    this.belongTaskId=belongTaskId;	}	@Column(name="upload_person_id")	public Integer getUploadPersonId() {	    return this.uploadPersonId;	}	public void setUploadPersonId(Integer uploadPersonId) {	    this.uploadPersonId=uploadPersonId;	}	@Column(name="create_time")	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	@Column(name="update_time")	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}	public ProjectsEntity getProjects() {	    return this.projects;	}	public void setProjects(ProjectsEntity projects) {	    this.projects=projects;	}
}
