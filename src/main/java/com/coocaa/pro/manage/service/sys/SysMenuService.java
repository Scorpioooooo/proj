package com.coocaa.pro.manage.service.sys;

import com.coocaa.pro.manage.entity.SysMenuEntity;
import com.coocaa.pro.manage.mapper.SysMenuMapper;
import com.coocaa.pro.manage.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>系统菜单 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysMenuService")
public class SysMenuService extends BaseService<SysMenuEntity> {

    private final static Logger log = Logger.getLogger(SysMenuService.class);

    @Autowired(required = false)
    private SysMenuMapper mapper;

    /**
     * 获取指定角色列表对应的菜单聚合,已去重
     *
     * @param roleIds
     * @return
     */
    public List<SysMenuEntity> getMenusByRole(List<Integer> roleIds) {

        return mapper.getMenusByRole(roleIds);
    }

    public SysMenuMapper getMapper() {
        return mapper;
    }

}
