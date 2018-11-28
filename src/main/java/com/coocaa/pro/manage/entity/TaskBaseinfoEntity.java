package com.coocaa.pro.manage.entity;

import java.io.Serializable;
import com.coocaa.fire.utils.plugin.annotation.Column;

/**
 * <br>
 * <b>功能：</b>基础任务表 Entity<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:46<br>
 * <b>详细说明：</b>无<br>
 */
public class TaskBaseinfoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
		private Integer id;//   	private Integer taskId;//   任务id	private String taskName;//   任务名称		@Column(name="id")	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	@Column(name="task_id")	public Integer getTaskId() {	    return this.taskId;	}	public void setTaskId(Integer taskId) {	    this.taskId=taskId;	}	@Column(name="task_name")	public String getTaskName() {	    return this.taskName;	}	public void setTaskName(String taskName) {	    this.taskName=taskName;	}
}
