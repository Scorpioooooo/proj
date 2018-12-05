package com.coocaa.pro.manage.mapper;


import com.coocaa.pro.manage.entity.SysRoleEntity;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>角色 Mapper<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 获取用户没有角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRoleEntity> getNotRolesByUserId(Integer userId);

    /**
     * 获取用户已有角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRoleEntity> getRolesByUserId(Integer userId);
}
