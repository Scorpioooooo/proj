package com.coocaa.pro.manage.common;

import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单权限相关
 *
 * @author Administrator
 */
@Component
public class MenuAuth {

    private static final Logger log = LoggerFactory.getLogger(MenuAuth.class);

    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;
    @Autowired(required = false)
    private SysRunService sysRunService;

    /**
     * 获取登录用户指定菜单的操作权限
     *
     * @param clzz
     * @param loginUser
     * @return
     */
    public List<SysRunEntity> getMenuTools(Class<?> clzz, SysUserEntity loginUser) {

        if (loginUser == null) {
            //连接已超时
            log.error("当前登陆用户已连接超时");
            return null;
        }
        Config config = clzz.getAnnotation(Config.class);
        Integer menuId = sysAdminMgrService.getMenuIdByElid(config.menuElId());
        List<SysRunEntity> sysRuns;
        if (loginUser.getSuperAdmin() == 1) {
            sysRuns = sysRunService.queryMenuRuns(menuId, true);
        } else {
            sysRuns = sysRunService.queryMenuRunsByMenuId(menuId, loginUser.getRoles(), true);
        }
        return sysRuns;
    }

    /**
     * 获取指定菜单的操作角色权限，可见btn
     *
     * @param menuElId
     * @param roles
     * @return
     */
    public List<SysRunEntity> getMenuTools(String menuElId, List<Integer> roles) {
        Integer menuId = sysAdminMgrService.getMenuIdByElid(new String[]{menuElId});
        List<SysRunEntity> sysRuns;
        sysRuns = sysRunService.queryMenuRunsByMenuId(menuId, roles, true);
        return sysRuns;
    }

    /**
     * 获取指定用户和菜单的数据操作权限，不可见
     *
     * @param menuElId
     * @param loginUser
     * @return
     */
    public List<SysRunEntity> getDataTools(String menuElId, SysUserEntity loginUser) throws Exception {
        Integer menuId = sysAdminMgrService.getMenuIdByElid(new String[]{menuElId});
        List<SysRunEntity> sysRuns;
        sysRuns = sysRunService.queryMenuRunsByMenuId(menuId, loginUser.getRoles(), false);
        return sysRuns;
    }

    public List<SysRunEntity> removeButton(List<SysRunEntity> buttons, List<SysRunEntity> retainButtons) {
        if (buttons == null || retainButtons == null) return buttons;
        buttons.retainAll(retainButtons);
        return buttons;
    }
}
