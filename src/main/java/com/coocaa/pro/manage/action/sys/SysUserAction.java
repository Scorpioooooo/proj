package com.coocaa.pro.manage.action.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coocaa.fire.utils.*;
import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.SortOperator;
import com.coocaa.fire.utils.plugin.WhereOperator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Compare;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Operator;
import com.coocaa.fire.utils.plugin.bean.QuerySortBean;
import com.coocaa.fire.utils.plugin.bean.QueryWhereBean;
import com.coocaa.pro.manage.action.BasicAction;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.AuthEnum;
import com.coocaa.pro.manage.common.AuthEnum.AuthorityEnum;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.common.MenuAuth;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.entity.*;
import com.coocaa.pro.manage.model.SysUserModel;
import com.coocaa.pro.manage.service.sys.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Config(menuElId = "userConfig")//相关配置
@RequestMapping("/sysUser")
public class SysUserAction extends BasicAction {

    private final static Logger log = Logger.getLogger(SysUserAction.class);

    @Autowired(required = false)
    private MenuAuth menuAuth;
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private SysUserService sysUserService;
    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysRoleService sysRoleService;
    @Autowired(required = false)
    private SysDeptService sysDeptService;
    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;

    /**
     * 系统用户视图
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/view")
    public String view(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
        map.put("depts", depts);
        map.put("path", "sysUser/sysUserView");
        return renderToJson(map);
    }

    /**
     * 获取菜单运行权限列表
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/getMenuInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMenuInfo(HttpServletRequest request) {
        SysUserEntity user = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
        List<SysRunEntity> sysRuns = menuAuth.getMenuTools(this.getClass(), user);

        return renderToJson(sysRuns);
    }

    /**
     * 普通查询分页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Auth(authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/pageList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dataPageList(HttpServletRequest request, SysUserModel model) throws Exception {

        Pager<SysUserEntity> pager = new Pager<SysUserEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), (entry.getValue() instanceof String ? Compare.LIKE : Compare.EQ), entry.getValue(), SysUserEntity.class));
        }
        //超级管理员处理
        whereOperator.addWhere(new QueryWhereBean(Operator.AND, "superAdmin", Compare.NEQ, 1, SysUserEntity.class));

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysUserEntity.class));
            pager.setSortOperator(sortOper);
        }

        sysUserService.queryByPage(pager);

        //设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", pager.getRowCount());
        jsonMap.put("rows", pager.getResults());
        return renderToJson(jsonMap);
    }

    /**
     * 增加记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.ADD)
    @RequestMapping("/add")
    public String add(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
        map.put("depts", depts);
        map.put("path", "sysUser/sysUserAdd");
        return renderToJson(map);
    }

    /**
     * 修改记录
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, @RequestParam Integer id) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        SysUserEntity entity = sysUserService.queryById(id);
        entity.setLoginPwd("");
        map.put("entity", JsonUtils.obj2Json(entity));
        List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
        map.put("depts", depts);
        map.put("path", "sysUser/sysUserEdit");
        return renderToJson(map);
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
    @RequestMapping(value = "/save", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String save(HttpServletRequest request, SysUserEntity entity) {
        try {
            if (entity.getUserId() == null) {
                String salt = RandomUtils.getNum(6);
                entity.setSalt(salt);
                entity.setLoginPwd(MD5Encode.getMD5Str(MD5Encode.getMD5Str(entity.getLoginPwd() + salt)));
                entity.setLoginCount(0);
                entity.setSuperAdmin(0);
                entity.setDateCreate(DateUtils.getCurDate());

                sysUserService.add(entity);
            } else {
                String salt = RandomUtils.getNum(6);
                entity.setSalt(salt);
                entity.setDateModify(DateUtils.getCurDate());
                entity.setLoginPwd(MD5Encode.getMD5Str(MD5Encode.getMD5Str(entity.getLoginPwd() + entity.getSalt())));
                sysUserService.updateBySelective(entity);
            }
            return ResultMessage.success("数据保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("数据保存异常");
        }
    }

    /**
     * 删除记录
     *
     * @param id
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.DELETE)
    @RequestMapping(value = "/remove", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String remove(Integer[] id, HttpServletRequest request) {
        try {
            sysUserService.deleteBatch(id);
            return ResultMessage.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("删除异常");
        }
    }

    /**
     * 获取用户没有角色列表
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/getNotRolesByUserId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getNotRolesByUserId(HttpServletRequest request, @RequestParam Integer userId) {

        List<SysRoleEntity> roles = sysRoleService.getNotRolesByUserId(userId);
        return renderToJson(roles);
    }

    /**
     * 获取用户已有角色列表
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/getRolesByUserId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRolesByUserId(HttpServletRequest request, @RequestParam Integer userId) {

        List<SysRoleEntity> roles = sysRoleService.getRolesByUserId(userId);
        return renderToJson(roles);
    }

    /**
     * 保存更新用户角色
     *
     * @param request
     * @param userId
     * @param roleIds
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
    @RequestMapping(value = "/saveUserRoles", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveUserRoles(HttpServletRequest request, @RequestParam Integer userId, @RequestParam(value = "roleIds", required = false) Integer[] roleIds) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<SysUserRoleEntity> entitys = new ArrayList<SysUserRoleEntity>();
            if (roleIds != null) {
                for (Integer roleId : roleIds) {
                    SysUserRoleEntity entity = new SysUserRoleEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    entitys.add(entity);
                }
            }
            sysAdminMgrService.saveUserRoles(userId, entitys);
            ResultMessage.success("保存成功", map);
        } catch (Exception e) {
            ResultMessage.fail("保存失败", map);
        }
        return renderToJson(map);
    }


    /**
     * 用户列表
     *
     * @param curPg
     * @param pgSize
     * @param queryData
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthEnum.AuthorityEnum.BROWSER)
    @RequestMapping(value = "/onePg", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String onePg(HttpServletRequest request, @RequestParam String curPg,
                        @RequestParam String pgSize,
                        @RequestParam String queryData
    ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", 11);

        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("ID", "1");
        data.put("name", "宁秋梅");
        data.put("userAccount", "ningqiumei");
        data.put("department", "端产品部");
        data.put("email", "ningqiumei@coocaa.com");
        data.put("IP", "192.168.1.2");
        data.put("forbidden", "否");
        data.put("loginCnt", "13");
        data.put("jobAbout", "软件开发");
        data.put("creatTime", "2018-09-08 20:12:00");
        data.put("lastUpdateTime", "2018-09-08 20:12:00");
        data.put("psInfo", "2018-09-08 20:12:00");
        jsonArray.add(data);

        data = new JSONObject();
        data.put("ID", "2");
        data.put("name", "谢仁斌");
        data.put("userAccount", "xierenbin");
        data.put("department", "端产品部");
        data.put("email", "xierenbin@coocaa.com");
        data.put("IP", "192.168.1.2");
        data.put("forbidden", "否");
        data.put("loginCnt", "13");
        data.put("jobAbout", "软件开发");
        data.put("creatTime", "2018-09-08 20:12:00");
        data.put("lastUpdateTime", "2018-09-08 20:12:00");
        data.put("psInfo", "2018-09-08 20:12:00");
        jsonArray.add(data);
        map.put("data", jsonArray);
        return renderToJson(map);
    }
}
