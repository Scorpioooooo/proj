package com.coocaa.pro.manage.service;

import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.QueryOperator;
import com.coocaa.pro.manage.mapper.BaseMapper;

import java.util.List;

public abstract class BaseService<T> {

    public abstract BaseMapper<T> getMapper();

    /**
     * 增加
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public T add(T entity) {
        getMapper().add(entity);
        return entity;
    }

    /**
     * 批量增加实体
     *
     * @param entitys
     * @return
     */
    public Integer addBatch(List<T> entitys) {

        return getMapper().addBatch(entitys);
    }

    /**
     * 更新所有
     *
     * @param entity
     * @throws Exception
     */
    public void update(T entity) {
        getMapper().update(entity);
    }

    /**
     * 更新部分
     *
     * @param entity
     * @throws Exception
     */
    public void updateBySelective(T entity) {
        getMapper().updateBySelective(entity);
    }

    /**
     * 删除
     *
     * @param ids
     * @throws Exception
     */
    public void delete(Object... ids) throws Exception {
        if (ids == null || ids.length < 1) {
            return;
        }
        for (Object id : ids) {
            getMapper().delete(id);
        }
    }

    /**
     * 根据条件批量删除
     *
     * @param entity 条件
     * @return 影响行数
     */
    public Integer deleteBatch(T entity) {

        return getMapper().deleteBatch(entity);
    }

    /**
     * 根据主键列表批量删除
     *
     * @param ids
     * @return
     */
    public Integer deleteBatch(Object[] ids) throws Exception {

        return getMapper().deleteBatchByIds(ids);
    }

    /**
     * 获取记录数
     *
     * @param operator
     * @return
     * @throws Exception
     */
    public int queryByCount(QueryOperator operator) {
        return getMapper().queryByCount(operator);
    }

    /**
     * 条件查询
     *
     * @param operator
     * @return
     * @throws Exception
     */
    public List<T> queryByList(QueryOperator operator) {

        return getMapper().queryByList(operator);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T queryById(Object id) {
        return getMapper().queryById(id);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public <U extends T> List<U> queryByPage(Pager<U> page) {
        List<U> pageList = getMapper().queryByPage(page);
        page.setResults(pageList);
        return pageList;
    }

    /**
     * 查询符合条件的第一条记录
     *
     * @param operator
     * @return
     */
    public T queryByOne(QueryOperator operator) {

        Pager<T> page = new Pager<T>();
        page.setPageSize(1);
        if (operator != null) {
            page.setWhereOperator(operator.getWhereOperator());
            page.setSortOperator(operator.getSortOperator());
        }

        List<T> resList = this.queryByPage(page);

        if (resList.size() > 0) {
            return resList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查找所有记录
     *
     * @return
     */
    public List<T> queryByAll(QueryOperator operator) {

        return getMapper().queryByAll(operator);
    }
}
