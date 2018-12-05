package com.coocaa.pro.manage.mapper;


import com.coocaa.pro.manage.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


/**
 * <br>
 * <b>功能：</b>系统菜单 Mapper<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 获取指定角色列表对应的菜单聚合,已去重
     *
     * @param roleIds
     * @return
     */
    public List<SysMenuEntity> getMenusByRole(List<Integer> roleIds);

    /**
     * @param parent 菜单父id
     * @param roleId 角色id
     * @return
     */
    List<HashMap<String, Object>> queryByRole(@Param("parent") Integer parent, @Param("roleId") Integer roleId);
}
