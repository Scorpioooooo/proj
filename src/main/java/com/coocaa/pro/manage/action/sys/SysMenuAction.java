package com.coocaa.pro.manage.action.sys;

import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.JsonUtils;
import com.coocaa.fire.utils.ReflectHelper;
import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.QueryOperator;
import com.coocaa.fire.utils.plugin.SortOperator;
import com.coocaa.fire.utils.plugin.WhereOperator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Operator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Compare;
import com.coocaa.fire.utils.plugin.bean.QuerySortBean;
import com.coocaa.fire.utils.plugin.bean.QueryWhereBean;
import com.coocaa.pro.manage.action.BasicAction;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.AuthEnum.AuthorityEnum;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.common.MenuAuth;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.entity.SysMenuEntity;
import com.coocaa.pro.manage.entity.SysMenuRunEntity;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.model.SysMenuModel;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysMenuService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Config(menuElId = "sysMenu")//相关配置
@RequestMapping("/sysMenu")
public class SysMenuAction extends BasicAction {

    private final static Logger log = Logger.getLogger(SysMenuAction.class);

    @Autowired(required = false)
    private MenuAuth menuAuth;
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private SysMenuService sysMenuService;
    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;


    /**
     * 系统菜单视图
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/view")
    public String list(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "sysMenu/sysMenuView");
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
     * 普通查询不分页
     *
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/dataList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dataList(HttpServletResponse response, SysMenuModel model) throws Exception {

        QueryOperator query = new QueryOperator();
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysMenuEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysMenuEntity.class));
            }
        }
        query.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysMenuEntity.class));
            query.setSortOperator(sortOper);
        }
        List<SysMenuEntity> list = sysMenuService.queryByAll(query);

        return renderToJson(list);
    }

    /**
     * 普通查询分页
     *
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/pageList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dataPageList(HttpServletResponse response, SysMenuModel model) throws Exception {

        Pager<SysMenuEntity> pager = new Pager<SysMenuEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysMenuEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysMenuEntity.class));
            }
        }

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysMenuEntity.class));
            pager.setSortOperator(sortOper);
        }

        sysMenuService.queryByPage(pager);

        //设置页面数据
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", pager.getRowCount());
        jsonMap.put("rows", pager.getResults());
        return renderToJson(jsonMap);
    }

    /**
     * 普通树表查询分页
     *
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/treePageList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dataTreePageList(HttpServletResponse response, SysMenuModel model) throws Exception {

        QueryOperator query = new QueryOperator();
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysMenuEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysMenuEntity.class));
            }
        }
        if (map.size() > 0) {
            whereOperator.addWhere(new QueryWhereBean(Operator.OR, "pmid", Compare.EQ, 0, SysMenuEntity.class));
        }

        query.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysMenuEntity.class));
            query.setSortOperator(sortOper);
        }

        List<SysMenuEntity> menus = sysMenuService.queryByAll(query);

        return JsonUtils.getListToTreeJson(menus, "menuId", "pmid", null);
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
        map.put("path", "sysMenu/sysMenuAdd");
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
        SysMenuEntity entity = sysMenuService.queryById(id);
        map.put("entity", JsonUtils.obj2Json(entity));
        map.put("path", "sysMenu/sysMenuEdit");
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
    public String save(HttpServletRequest request, SysMenuEntity entity) {
        try {
            if (entity.getMenuId() == null) {
                sysMenuService.add(entity);
            } else {
                sysMenuService.updateBySelective(entity);
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
            sysMenuService.deleteBatch(id);
            return ResultMessage.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("删除异常");
        }
    }

    /**
     * 获取菜单没有操作列表
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/getNotMenuByRunId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getNotMenuByRunId(HttpServletRequest request, @RequestParam Integer menuId) {

        List<SysRunEntity> roles = sysRunService.getNotRunByMenuId(menuId);
        return renderToJson(roles);
    }

    /**
     * 获取用户已有角色列表
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/getMenuByRunId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRolesByUserId(HttpServletRequest request, @RequestParam Integer menuId) {

        List<SysRunEntity> roles = sysRunService.getRunByMenuId(menuId);
        return renderToJson(roles);
    }

    /**
     * 保存更新用户角色
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
    @RequestMapping(value = "/saveMenuRun", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveMenuRun(HttpServletRequest request, @RequestParam Integer menuId, @RequestParam(value = "runIds", required = false) Integer[] runIds) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<SysMenuRunEntity> entitys = new ArrayList<SysMenuRunEntity>();
            if (runIds != null) {
                for (Integer runId : runIds) {
                    SysMenuRunEntity entity = new SysMenuRunEntity();
                    entity.setMenuId(menuId);
                    entity.setRunId(runId);
                    entitys.add(entity);
                }
            }
            sysAdminMgrService.saveMenuRuns(menuId, entitys);
            ResultMessage.success("保存成功", map);
        } catch (Exception e) {
            ResultMessage.fail("保存失败", map);
        }
        return renderToJson(map);
    }


    /**
     * <b>updateSeq</b><br/>
     * <p>根据主键更新排序号</p>
     * <b>date：</b>2014-4-28 <br/>
     *
     * @param request
     * @param did
     * @param seq
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
    @RequestMapping(value = "/updateSeq", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateSeq(HttpServletRequest request, String did, Integer seq) {
        try {
            if (did != null) {
                String[] ids = did.split("-");
                SysMenuEntity entity = new SysMenuEntity();
                entity.setMenuId(NumberUtils.toInt(ids[0]));

                seq = (seq == null ? 0 : seq);
                if (ids[1] != null && "seq".equals(ids[1])) {
                    entity.setSeq(seq);
                } else if (ids[1] != null && "ab".equals(ids[1])) {
                    entity.setDisabled(seq);
                }
                sysMenuService.updateBySelective(entity);
            }

            return ResultMessage.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("更新失败");
        }
    }
}
