package com.coocaa.pro.manage.mapper;


import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.QueryOperator;

import java.util.List;

public interface BaseMapper<T> {

    /**
     * 增加实体
     *
     * @param entity
     * @return
     */
    public void add(T entity);

    /**
     * 批量增加实体
     *
     * @param entitys
     * @return
     */
    public Integer addBatch(List<T> entitys);

    /**
     * 修改完成实体
     *
     * @param entity
     * @return
     */
    public Integer update(T entity);

    /**
     * 修改部分实体
     *
     * @param entity
     * @return
     */
    public Integer updateBySelective(T entity);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public Integer delete(Object id);

    /**
     * 根据主键列表批量删除
     *
     * @param ids
     * @return
     */
    public Integer deleteBatchByIds(Object[] ids);

    /**
     * 根据条件批量删除
     *
     * @param entity
     * @return
     */
    public Integer deleteBatch(T entity);

    /**
     * 查找所有记录
     *
     * @return
     */
    public List<T> queryByAll(QueryOperator operator);

    /**
     * 根据主健查询
     *
     * @param id
     * @return
     */
    public T queryById(Object id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public <U extends T> List<U> queryByPage(Pager<U> page);

    /**
     * 获取记录数
     *
     * @param operator
     * @return
     */
    public Integer queryByCount(QueryOperator operator);

    /**
     * 按实体查询
     *
     * @param operator
     * @return
     */
    public List<T> queryByList(QueryOperator operator);

}
