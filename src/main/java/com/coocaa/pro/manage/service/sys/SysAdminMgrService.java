package com.coocaa.pro.manage.service.sys;


import com.coocaa.fire.utils.DateUtils;
import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.MD5Util;
import com.coocaa.fire.utils.Utils;
import com.coocaa.fire.utils.plugin.QueryOperator;
import com.coocaa.fire.utils.plugin.SortOperator;
import com.coocaa.fire.utils.plugin.WhereOperator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Compare;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Operator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Sort;
import com.coocaa.fire.utils.plugin.bean.QuerySortBean;
import com.coocaa.fire.utils.plugin.bean.QueryWhereBean;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class SysAdminMgrService {

    private final static Logger log = Logger.getLogger(SysRoleMenuRunService.class);

    @Autowired(required = false)
    private SysMenuService sysMenuService;
    @Autowired(required = false)
    private SysUserService sysUserService;
    @Autowired(required = false)
    private SysUserRoleService sysUserRoleService;
    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysMenuRunService sysMenuRunService;
    @Autowired(required = false)
    private SysRoleMenuRunService sysRoleMenuRunService;
    @Autowired(required = false)
    private SysLogLoginService sysLogLoginService;

    /**
     * 会员登录
     *
     * @param request
     * @param account
     * @param pwd
     * @return
     */
    public Boolean userLogin(HttpServletRequest request, String account, String pwd) throws NoSuchAlgorithmException {


        QueryOperator query = new QueryOperator();
        WhereOperator where = new WhereOperator();
        where.addWhere(new QueryWhereBean(Operator.AND, "loginName", Compare.EQ, account, SysUserEntity.class));
        query.setWhereOperator(where);
        SysUserEntity user = sysUserService.queryByOne(query);
        Boolean isLogin = false;
        if (user != null) {
            String md5PWD = MD5Util.md5(MD5Util.md5(pwd + user.getSalt()));
            if (user.getLoginPwd().equals(md5PWD)) {
                //写入SESSION
                HttpSessionUtils.setSessionValue(request, Constant.SESSION_LOGIN_USER, user);

                Date loginTime = DateUtils.getCurDate();
                String loginIp = Utils.getIpAddr(request);
                SysUserEntity upUserEntity = new SysUserEntity();
                upUserEntity.setUserId(user.getUserId());
                upUserEntity.setLoginTime(loginTime);
                upUserEntity.setLoginIp(loginIp);
                upUserEntity.setLoginCount(user.getLoginCount() + 1);

                try {
                    sysUserService.updateBySelective(upUserEntity);
                    SysLogLoginEntity login = new SysLogLoginEntity();
                    login.setUserName(user.getLoginName());
                    login.setUserId(user.getUserId());
                    login.setLoginTime(loginTime);
                    login.setLoginIp(loginIp);
                    login.setLoginType(0);
                    login.setUserAgent(request.getHeader("user-agent"));
                    login.setRemark("登录");
                    sysLogLoginService.add(login);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("用户登录时写入数据失败！");
                }
                isLogin = true;
            }
        }
        return isLogin;
    }

    /**
     * 根据ELID获取导航菜单编号
     *
     * @param elid
     * @return
     */
    public Integer getMenuIdByElid(String[] elid) {

        String key = "cache_menu_elid_" + StringUtils.join(elid, "_");
        //MemcachedCMgr memcachedCMgr = MemcachedCMgr.getInstance();
        //Integer menuId = (Integer) MemcachedCMgr.getInstance().get(key);
        Integer menuId = null;
        if (menuId == null) {
            QueryOperator query = new QueryOperator();
            WhereOperator where = new WhereOperator();
            where.addWhere(new QueryWhereBean(Operator.AND, "elid",
                    (elid.length > 1 ? Compare.IN : Compare.EQ), elid,
                    SysMenuEntity.class));
            query.setWhereOperator(where);
            SysMenuEntity menu = sysMenuService.queryByOne(query);
            if (menu != null) {
                menuId = menu.getMenuId();
                // MemcachedCMgr.getInstance().set(key, CacheEnum.CacheTimeEnum.CACHE_EXP_H.key, menuId);
            }
        }
        return menuId;
    }

    /**
     * 修改角色权限
     *
     * @param roleId   -角色编号
     * @param menuRuns -菜单角色列表
     * @throws Exception
     */
    public void updateRoleAuth(Integer roleId, List<Map<String, Integer>> menuRuns) throws Exception {
        //删除原角色权限
        sysRoleMenuRunService.delete(roleId);
        //增加新的角色权限
        if (menuRuns.size() > 0) {
            List<SysRoleMenuRunEntity> entitys = new ArrayList<SysRoleMenuRunEntity>();
            for (Map<String, Integer> map : menuRuns) {
                SysRoleMenuRunEntity entity = new SysRoleMenuRunEntity();
                entity.setRoleId(roleId);
                Object o = map.get("menuId");
                Integer menuId = Integer.valueOf(o.toString());
                entity.setMenuId(menuId);
                Object m = map.get("runId");
                Integer runId = Integer.valueOf(m.toString());
                entity.setRunId(runId);
                entitys.add(entity);
            }
            sysRoleMenuRunService.addBatch(entitys);
        }
    }

    /**
     * 保存修改用户角色
     *
     * @param userId
     * @param userRoles
     * @throws Exception
     */
    public void saveUserRoles(Integer userId, List<SysUserRoleEntity> userRoles) throws Exception {
        //删除原角色
        sysUserRoleService.delete(userId);
        if (userRoles.size() > 0) {
            //增加新角色
            sysUserRoleService.addBatch(userRoles);
        }
    }

    /**
     * 保存修改菜单操作
     *
     * @param menuId
     * @param menusRuns
     * @throws Exception
     */
    public void saveMenuRuns(Integer menuId, List<SysMenuRunEntity> menusRuns) throws Exception {
        //删除原角色
        sysMenuRunService.delete(menuId);
        if (menusRuns.size() > 0) {
            //增加新角色
            sysMenuRunService.addBatch(menusRuns);
        }
    }

    /**
     * 获取当前用户有权限的菜单列表
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public String getUserMenusByUser(SysUserEntity userInfo) throws Exception {

        List<SysMenuEntity> list = new ArrayList<SysMenuEntity>();
        if (userInfo.getSuperAdmin() == 1) {
            WhereOperator where = new WhereOperator();
            where.addWhere(new QueryWhereBean(Operator.AND, "disabled", Compare.EQ, 0, SysMenuEntity.class));

            SortOperator sort = new SortOperator();
            sort.add(new QuerySortBean("pmid", Sort.ASC.key, SysMenuEntity.class));
            sort.add(new QuerySortBean("seq", Sort.ASC.key, SysMenuEntity.class));

            QueryOperator query = new QueryOperator();
            query.setWhereOperator(where);
            query.setSortOperator(sort);
            list = sysMenuService.queryByAll(query);
        } else {
            List<Integer> rids = userInfo.getRoles();
            if (rids != null && rids.size() > 0) {
                list = sysMenuService.getMenusByRole(rids);
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(getMenusToString(list, null));
        sb.append("]");
        return sb.toString().replaceAll(",\"children\":\\[]", "").replaceAll(",]", "]");
    }

    /**
     * 获取所有可用菜单及操作，返回JSON
     *
     * @return
     * @throws Exception
     */
    public String getMenuAuths(Integer roleId) throws Exception {

        WhereOperator where = new WhereOperator();
        where.addWhere(new QueryWhereBean(Operator.AND, "disabled", Compare.EQ, 0, SysMenuEntity.class));
        QueryOperator query = new QueryOperator();
        query.setWhereOperator(where);
        //所有的菜单
        List<SysMenuEntity> menus = sysMenuService.queryByAll(query);

        QuerySortBean sortBean = new QuerySortBean("seq", Sort.ASC.key, SysRunEntity.class);
        SortOperator sortRun = new SortOperator();
        sortRun.add(sortBean);
        QueryOperator queryRun = new QueryOperator();
        queryRun.setSortOperator(sortRun);
        //菜单操作权限
        List<SysRunEntity> runs = sysRunService.queryByAll(queryRun);
        //所有菜单的操作列表
        List<SysMenuRunEntity> menuRuns = sysMenuRunService.queryByAll(null);
        //当前角色拥有的菜单权限
        WhereOperator rmrswhere = new WhereOperator();
        rmrswhere.addWhere(new QueryWhereBean(Operator.AND, "roleId", Compare.EQ, roleId, SysRoleMenuRunEntity.class));
        QueryOperator rmrsQuery = new QueryOperator();
        rmrsQuery.setWhereOperator(rmrswhere);
        List<SysRoleMenuRunEntity> roleMenuRuns = sysRoleMenuRunService.queryByAll(rmrsQuery);

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(getMenusToString(menus, runs, menuRuns, roleMenuRuns, roleId, null));
        sb.append("]");

        return sb.toString().replaceAll(",\"children\":\\[]", "").replaceAll(",]", "]");
    }

    /**
     * 通过角色id获取当前角色权限赋值情况 <br/>
     * add by liuzinong at 2016年9月20日 09:04:33 <br/>
     * 结果集格式来自 {@link SysAdminMgrService#getMenuAuths(Integer)}
     *
     * @param roleId 角色id
     * @return
     */
    public Object getMenuAuths2(Integer roleId) {
        //一级目录
        List<HashMap<String, Object>> datas = sysMenuService.getMapper().queryByRole(0, roleId);
        return this.prepareData(datas, roleId);
    }

    /**
     * 处理数据
     *
     * @param datas  数据库查询结果集
     * @param roleId 角色id
     * @return
     */
    private List<Map<String, Object>> prepareData(List<HashMap<String, Object>> datas, Integer roleId) {
        // key ：menuid  value ：options
        Map<Object, List<HashMap<String, Object>>> result = new HashMap<>();

        //把菜单id相同的加入同一list
        //方便后续结果处理
        if (CollectionUtils.isNotEmpty(datas)) {
            for (HashMap<String, Object> data : datas) {
                Object menuId = data.get("menuId");
                if (result.get(menuId) != null) {
                    result.get(menuId).add(data);
                } else {
                    List<HashMap<String, Object>> list = new ArrayList<>();
                    list.add(data);
                    result.put(menuId, list);
                }
            }
        }

        List<Map<String, Object>> target = new ArrayList<>();
        for (Map.Entry<Object, List<HashMap<String, Object>>> entry : result.entrySet()) {
            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                HashMap<String, Object> first = entry.getValue().get(0);
                //菜单单条数据
                Map<String, Object> that = new HashMap<>();
                that.put("menuId", entry.getKey());
                that.put("text", first.get("menuName"));
                that.put("iconCls", first.get("iconClass"));
                //父id
                int parent = Integer.parseInt(first.get("pmid") + "");
                if (parent == 0) {
                    //递归调用
                    that.put("children", this.prepareData(sysMenuService.getMapper().queryByRole(Integer.parseInt(that.get("menuId") + ""), roleId), roleId));
                }
                for (HashMap<String, Object> map : entry.getValue()) {
                    int status = 0;
                    Object x = map.get("x"); // 1当前菜单有配置操作选项  0 当前菜单没有配置
                    if (x != null && Integer.parseInt(x + "") == 0) {
                        status = -1;
                    } else if (x != null && Integer.parseInt(x + "") == 1) {
                        // y 当前角色对当前菜单有操作选项
                        if (1 == Integer.parseInt(map.get("y") + "")) {
                            status = 1;
                        }
                    }
                    that.put(map.get("runComm") + "", status);
                }

                target.add(that);
            }
        }
        return target;
    }


    /**
     * 递归生成JSON格式菜单字符串
     *
     * @param menuList
     * @param menuEntity
     * @return
     */
    private String getMenusToString(List<SysMenuEntity> menuList, List<SysRunEntity> runs, List<SysMenuRunEntity> menuRuns, List<SysRoleMenuRunEntity> roleMenuRuns, Integer roleId, SysMenuEntity menuEntity) {
        StringBuffer sb = new StringBuffer();
        SysMenuEntity menu = null;
        if (menuEntity == null) {
            for (SysMenuEntity me : menuList) {
                menu = me;
                if (menu.getPmid() == 0) {
                    sb.append("{\"text\":\"");
                    sb.append(me.getMenuName());
                    sb.append("\",\"menuId\":\"");
                    sb.append(me.getMenuId());
                    sb.append("\",\"elid\":\"");
                    sb.append(me.getElid());
                    sb.append("\",\"title\":\"");
                    sb.append(me.getTabTitle());
                    sb.append("\",\"iconCls\":\"");
                    sb.append(me.getIconClass());
                    sb.append("\",\"roleId\":\"");
                    sb.append(roleId);
                    for (SysRunEntity sysRunEntity : runs) {
                        sb.append("\",\"" + sysRunEntity.getRunComm() + "\":\"");
                        Boolean isMenuRun = false;
                        for (SysMenuRunEntity menuRunEntity : menuRuns) {
                            if (menu.getMenuId() == menuRunEntity.getMenuId() && sysRunEntity.getRunId() == menuRunEntity.getRunId()) {
                                isMenuRun = true;
                            }
                        }
                        if (isMenuRun) {
                            Boolean isRoleMenuRun = false;
                            for (SysRoleMenuRunEntity roleMenuRunEntity : roleMenuRuns) {
                                if (menu.getMenuId() == roleMenuRunEntity.getMenuId() && sysRunEntity.getRunId() == roleMenuRunEntity.getRunId()) {
                                    isRoleMenuRun = true;
                                }
                            }
                            if (isRoleMenuRun) {
                                sb.append("1");//角色有菜单操作权限
                            } else {
                                sb.append("0");//角色无菜单操作权限
                            }
                        } else {
                            sb.append("-1");//菜单没有该操作
                        }
                    }
                    sb.append("\",\"children\":[");
                    sb.append(getMenusToString(menuList, runs, menuRuns, roleMenuRuns, roleId, menu));
                    sb.append("]");
                    sb.append("},");
                }
            }
        } else {
            for (SysMenuEntity me : menuList) {
                menu = me;
                if (String.valueOf(menuEntity.getMenuId()).equals(String.valueOf(menu.getPmid()))) {
                    sb.append("{\"text\":\"");
                    sb.append(me.getMenuName());
                    sb.append("\",\"menuId\":\"");
                    sb.append(me.getMenuId());
                    sb.append("\",\"elid\":\"");
                    sb.append(me.getElid());
                    sb.append("\",\"title\":\"");
                    sb.append(me.getTabTitle());
                    sb.append("\",\"iconCls\":\"");
                    sb.append(me.getIconClass());
                    sb.append("\",\"roleId\":\"");
                    sb.append(roleId);
                    for (SysRunEntity sysRunEntity : runs) {
                        sb.append("\",\"" + sysRunEntity.getRunComm() + "\":\"");
                        Boolean isMenuRun = false;
                        for (SysMenuRunEntity menuRunEntity : menuRuns) {
                            if (menu.getMenuId() == menuRunEntity.getMenuId() && sysRunEntity.getRunId() == menuRunEntity.getRunId()) {
                                isMenuRun = true;
                            }
                        }
                        if (isMenuRun) {
                            Boolean isRoleMenuRun = false;
                            for (SysRoleMenuRunEntity roleMenuRunEntity : roleMenuRuns) {
                                if (menu.getMenuId() == roleMenuRunEntity.getMenuId() && sysRunEntity.getRunId() == roleMenuRunEntity.getRunId()) {
                                    isRoleMenuRun = true;
                                }
                            }
                            if (isRoleMenuRun) {
                                sb.append("1");
                            } else {
                                sb.append("0");
                            }
                        } else {
                            sb.append("-1");
                        }
                    }
                    sb.append("\",\"children\":[");
                    sb.append(getMenusToString(menuList, runs, menuRuns, roleMenuRuns, roleId, menu));
                    sb.append("]");
                    sb.append("},");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 递归生成JSON格式菜单字符串
     *
     * @param menuList
     * @param menuEntity
     * @return
     */
    private String getMenusToString(List<SysMenuEntity> menuList, SysMenuEntity menuEntity) {
        StringBuffer sb = new StringBuffer();
        SysMenuEntity menu = null;
        if (menuEntity == null) {
            for (SysMenuEntity me : menuList) {
                menu = me;
                if (menu.getPmid() == 0) {
                    sb.append("{\"text\":\"");
                    sb.append(me.getMenuName());
                    sb.append("\",\"id\":\"");
                    sb.append(me.getElid());
                    sb.append("\",\"title\":\"");
                    sb.append(me.getTabTitle());
                    sb.append("\",\"icon\":\"");
                    sb.append(me.getIconClass());
                    sb.append("\",\"url\":\"");
                    sb.append(me.getIframeUrl());
                    sb.append("\",\"children\":[");
                    sb.append(getMenusToString(menuList, menu));
                    sb.append("]");
                    sb.append("},");
                }
            }
        } else {
            for (SysMenuEntity me : menuList) {
                menu = me;
                if (String.valueOf(menuEntity.getMenuId()).equals(String.valueOf(menu.getPmid()))) {
                    sb.append("{\"text\":\"");
                    sb.append(me.getMenuName());
                    sb.append("\",\"id\":\"");
                    sb.append(me.getElid());
                    sb.append("\",\"iconCls\":\"");
                    sb.append(me.getIconClass());
                    sb.append("\",\"attributes\":{");
                    sb.append("\"tabTitle\":\"");
                    sb.append(me.getTabTitle());
                    sb.append("\",\"menuId\":\"");
                    sb.append(me.getMenuId());
                    sb.append("\",\"tabIcon\":\"");
                    sb.append(me.getTabIcon());
                    sb.append("\",\"modle\":\"");
                    sb.append(me.getModle());
                    sb.append("\",\"iframeUrl\":\"");
                    sb.append(me.getIframeUrl());
                    sb.append("\"},\"children\":[");
                    sb.append(getMenusToString(menuList, menu));
                    sb.append("]");
                    sb.append("},");
                }
            }
        }
        return sb.toString();
    }
}
