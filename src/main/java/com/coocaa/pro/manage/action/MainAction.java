package com.coocaa.pro.manage.action;

import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainAction extends BasicAction {

    private final static Logger log = Logger.getLogger(MainAction.class);

    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;
    @Autowired(required = false)


    /**
     * 获取系统菜单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMenus(HttpServletRequest request) {
        String menus = "[]";
        try {
            SysUserEntity userInfo = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
            menus = sysAdminMgrService.getUserMenusByUser(userInfo);
        }
        catch (Exception e) {
            log.error("获取系统菜单失败");
        }
        return menus;
    }


}
