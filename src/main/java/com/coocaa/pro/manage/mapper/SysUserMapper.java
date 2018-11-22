package com.coocaa.pro.manage.mapper;


import com.coocaa.pro.manage.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>操作用户 Mapper<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:45<br>
 * <b>详细说明：</b>无<br>
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    //通过角色查询用户email
    List<String> queryUserRoleEmail(@Param("roleid") Integer roleid);
}
