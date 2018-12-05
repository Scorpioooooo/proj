package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;import java.util.Date;/** * <br> * <b>功能：</b>任务表 Entity<br> * <b>作者：</b>bean creater<br> * <b>日期：</b>2018-11-29 15:19:11<br> * <b>详细说明：</b>无<br> */public class TasksEntity implements Serializable {    private static final long serialVersionUID = 1L;    private Integer id;//    private Integer taskId;//   任务id    private Integer belongProjectId;//   归属项目id 1:1    private Integer taskAsignPersonId;//   任务负责人    private String taskAsignPersonHistoryIds;//   指派任务历史负责人    private Integer taskLevel;//   任务级别，任务分两级。    private Integer taskParentId;//   上级任务id    private String taskName;//   任务名称    private String taskDiscription;//   任务描述    private int taskGrade;//   任务重要等级    private Date taskBeginTime;//   任务计划起始时间    private Date taskEndTime;//   任务预计结束时间    private Date taskRealBeginTime;//   任务实际开始时间    private Date taskRealEndTime;//   任务实际结束时间    private Date createtime;//   本信息创建时间    private Date updatetime;//   本条信息更新时间    private Integer taskIsclosed;//   任务是否结束 1: 结束 0：未结束    @Column(name = "id")    public Integer getId() {        return this.id;    }    public void setId(Integer id) {        this.id = id;    }    @Column(name = "task_id")    public Integer getTaskId() {        return this.taskId;    }    public void setTaskId(Integer taskId) {        this.taskId = taskId;    }    @Column(name = "belong_project_id")    public Integer getBelongProjectId() {        return this.belongProjectId;    }    public void setBelongProjectId(Integer belongProjectId) {        this.belongProjectId = belongProjectId;    }    @Column(name = "task_asign_person_id")    public Integer getTaskAsignPersonId() {        return this.taskAsignPersonId;    }    public void setTaskAsignPersonId(Integer taskAsignPersonId) {        this.taskAsignPersonId = taskAsignPersonId;    }    @Column(name = "task_asign_person_history_ids")    public String getTaskAsignPersonHistoryIds() {        return this.taskAsignPersonHistoryIds;    }    public void setTaskAsignPersonHistoryIds(String taskAsignPersonHistoryIds) {        this.taskAsignPersonHistoryIds = taskAsignPersonHistoryIds;    }    @Column(name = "task_level")    public Integer getTaskLevel() {        return this.taskLevel;    }    public void setTaskLevel(Integer taskLevel) {        this.taskLevel = taskLevel;    }    @Column(name = "task_parent_id")    public Integer getTaskParentId() {        return this.taskParentId;    }    public void setTaskParentId(Integer taskParentId) {        this.taskParentId = taskParentId;    }    @Column(name = "task_name")    public String getTaskName() {        return this.taskName;    }    public void setTaskName(String taskName) {        this.taskName = taskName;    }    @Column(name = "task_discription")    public String getTaskDiscription() {        return this.taskDiscription;    }    public void setTaskDiscription(String taskDiscription) {        this.taskDiscription = taskDiscription;    }    public int getTaskGrade() {        return taskGrade;    }    public void setTaskGrade(int taskGrade) {        this.taskGrade = taskGrade;    }    @Column(name = "task_begin_time")    public Date getTaskBeginTime() {        return this.taskBeginTime;    }    public void setTaskBeginTime(Date taskBeginTime) {        this.taskBeginTime = taskBeginTime;    }    @Column(name = "task_end_time")    public Date getTaskEndTime() {        return this.taskEndTime;    }    public void setTaskEndTime(Date taskEndTime) {        this.taskEndTime = taskEndTime;    }    @Column(name = "task_real_begin_time")    public Date getTaskRealBeginTime() {        return this.taskRealBeginTime;    }    public void setTaskRealBeginTime(Date taskRealBeginTime) {        this.taskRealBeginTime = taskRealBeginTime;    }    @Column(name = "task_real_end_time")    public Date getTaskRealEndTime() {        return this.taskRealEndTime;    }    public void setTaskRealEndTime(Date taskRealEndTime) {        this.taskRealEndTime = taskRealEndTime;    }    @Column(name = "createtime")    public Date getCreatetime() {        return this.createtime;    }    public void setCreatetime(Date createtime) {        this.createtime = createtime;    }    @Column(name = "updatetime")    public Date getUpdatetime() {        return this.updatetime;    }    public void setUpdatetime(Date updatetime) {        this.updatetime = updatetime;    }    @Column(name = "task_isclosed")    public Integer getTaskIsclosed() {        return this.taskIsclosed;    }    public void setTaskIsclosed(Integer taskIsclosed) {        this.taskIsclosed = taskIsclosed;    }}