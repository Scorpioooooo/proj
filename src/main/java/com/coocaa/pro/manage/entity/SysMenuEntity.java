package com.coocaa.pro.manage.entity;import com.coocaa.fire.utils.plugin.annotation.Column;import java.io.Serializable;/** * <br> * <b>功能：</b>系统菜单 entity<br> * <b>作者：</b>siber.xu<br> * <b>日期：</b>2014-02-20 16:38:36<br> * <b>详细说明：</b>无<br> */public class SysMenuEntity implements Serializable {    private static final long serialVersionUID = 1L;    private Integer menuId;//   菜单编号    private String menuName;//   菜单名称    private String elid;//   元素ID    private String iconClass;//   节点图标样式    private String tabId;//   TAB页ID    private String tabTitle;//   TAB页标题    private String tabIcon;//   TAB页图标样式    private String iframeUrl;//   框架中打开URL    private Integer seq;//   排序    private Integer modle;//   打开模型0展开1TAB中打开2执行    private Integer disabled;//   0正常1禁用    private Integer pmid;//   菜单父编号    @Column(name = "menu_id")    public Integer getMenuId() {        return this.menuId;    }    public void setMenuId(Integer menuId) {        this.menuId = menuId;    }    @Column(name = "menu_name")    public String getMenuName() {        return this.menuName;    }    public void setMenuName(String menuName) {        this.menuName = menuName;    }    @Column(name = "elid")    public String getElid() {        return this.elid;    }    public void setElid(String elid) {        this.elid = elid;    }    @Column(name = "icon_class")    public String getIconClass() {        return this.iconClass;    }    public void setIconClass(String iconClass) {        this.iconClass = iconClass;    }    @Column(name = "tab_id")    public String getTabId() {        return this.tabId;    }    public void setTabId(String tabId) {        this.tabId = tabId;    }    @Column(name = "tab_title")    public String getTabTitle() {        return this.tabTitle;    }    public void setTabTitle(String tabTitle) {        this.tabTitle = tabTitle;    }    @Column(name = "tab_icon")    public String getTabIcon() {        return this.tabIcon;    }    public void setTabIcon(String tabIcon) {        this.tabIcon = tabIcon;    }    @Column(name = "iframe_url")    public String getIframeUrl() {        return this.iframeUrl;    }    public void setIframeUrl(String iframeUrl) {        this.iframeUrl = iframeUrl;    }    @Column(name = "seq")    public Integer getSeq() {        return this.seq;    }    public void setSeq(Integer seq) {        this.seq = seq;    }    @Column(name = "modle")    public Integer getModle() {        return this.modle;    }    public void setModle(Integer modle) {        this.modle = modle;    }    @Column(name = "disabled")    public Integer getDisabled() {        return this.disabled;    }    public void setDisabled(Integer disabled) {        this.disabled = disabled;    }    @Column(name = "pmid")    public Integer getPmid() {        return this.pmid;    }    public void setPmid(Integer pmid) {        this.pmid = pmid;    }}