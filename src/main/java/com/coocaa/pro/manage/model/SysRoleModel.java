package com.coocaa.pro.manage.model;import java.util.Date;/** * <br> * <b>功能：</b>角色 Model<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-02-20 15:06:44<br> * <b>详细说明：</b>无<br> */public class SysRoleModel extends BaseModel {    private Integer roleId;//   角色编号    private String roleName;//   角色名称    private Integer seq;//   排序号    private String roleDesc;//   说明    private Integer createUid;//   创建人    private Date createDate;//   创建时间    private Integer modifyUid;//   修改人    private Date modifyDate;//   修改时间    public Integer getRoleId() {        return this.roleId;    }    public void setRoleId(Integer roleId) {        this.roleId = roleId;    }    public String getRoleName() {        return this.roleName;    }    public void setRoleName(String roleName) {        this.roleName = roleName;    }    public Integer getSeq() {        return this.seq;    }    public void setSeq(Integer seq) {        this.seq = seq;    }    public String getRoleDesc() {        return this.roleDesc;    }    public void setRoleDesc(String roleDesc) {        this.roleDesc = roleDesc;    }    public Integer getCreateUid() {        return this.createUid;    }    public void setCreateUid(Integer createUid) {        this.createUid = createUid;    }    public Date getCreateDate() {        return this.createDate;    }    public void setCreateDate(Date createDate) {        this.createDate = createDate;    }    public Integer getModifyUid() {        return this.modifyUid;    }    public void setModifyUid(Integer modifyUid) {        this.modifyUid = modifyUid;    }    public Date getModifyDate() {        return this.modifyDate;    }    public void setModifyDate(Date modifyDate) {        this.modifyDate = modifyDate;    }}