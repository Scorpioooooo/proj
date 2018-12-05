package com.coocaa.pro.manage.model;

public class BaseModel {
    //当前页
    private Integer page = 1;
    //每页记录数
    private Integer rows = 10;
    //排序字段
    private String sort;
    //排序方式
    private String order;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
