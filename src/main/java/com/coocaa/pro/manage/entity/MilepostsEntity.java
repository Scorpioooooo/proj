package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;import java.util.Date;/** * <br> * <b>功能：</b>里程碑表 Entity<br> * <b>作者：</b>bean creater<br> * <b>日期：</b>2018-11-29 15:19:10<br> * <b>详细说明：</b>无<br> */public class MilepostsEntity implements Serializable {    private static final long serialVersionUID = 1L;    private Integer id;//    private Integer milepostId;//   里程碑id    private String milepostDescription;//   里程碑描述    private Integer orderby;//   排序    private Integer resolved;//   是否已经达成    private Date resolveTime;//   里程碑解决时间    private Date createTime;//   创建时间    private Date updateTime;//   更新时间    @Column(name = "id")    public Integer getId() {        return this.id;    }    public void setId(Integer id) {        this.id = id;    }    @Column(name = "milepost_id")    public Integer getMilepostId() {        return this.milepostId;    }    public void setMilepostId(Integer milepostId) {        this.milepostId = milepostId;    }    @Column(name = "milepost_description")    public String getMilepostDescription() {        return this.milepostDescription;    }    public void setMilepostDescription(String milepostDescription) {        this.milepostDescription = milepostDescription;    }    @Column(name = "orderby")    public Integer getOrderby() {        return this.orderby;    }    public void setOrderby(Integer orderby) {        this.orderby = orderby;    }    @Column(name = "resolved")    public Integer getResolved() {        return this.resolved;    }    public void setResolved(Integer resolved) {        this.resolved = resolved;    }    @Column(name = "resolve_time")    public Date getResolveTime() {        return this.resolveTime;    }    public void setResolveTime(Date resolveTime) {        this.resolveTime = resolveTime;    }    @Column(name = "create_time")    public Date getCreateTime() {        return this.createTime;    }    public void setCreateTime(Date createTime) {        this.createTime = createTime;    }    @Column(name = "update_time")    public Date getUpdateTime() {        return this.updateTime;    }    public void setUpdateTime(Date updateTime) {        this.updateTime = updateTime;    }}