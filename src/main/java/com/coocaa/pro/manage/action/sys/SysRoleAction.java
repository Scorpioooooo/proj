package com.coocaa.pro.manage.action.sys;


import com.coocaa.fire.utils.DateUtils;
import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.JsonUtils;
import com.coocaa.fire.utils.ReflectHelper;
import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.QueryOperator;
import com.coocaa.fire.utils.plugin.SortOperator;
import com.coocaa.fire.utils.plugin.WhereOperator;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Compare;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Operator;
import com.coocaa.fire.utils.plugin.bean.QuerySortBean;
import com.coocaa.fire.utils.plugin.bean.QueryWhereBean;
import com.coocaa.pro.manage.action.BasicAction;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.AuthEnum.AuthorityEnum;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.common.MenuAuth;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.entity.SysRoleEntity;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.model.SysRoleModel;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysRoleService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Config(menuElId = "roleConfig")//相关配置
@RequestMapping("/sysRole")
public class SysRoleAction extends BasicAction {

    private final static Logger log = Logger.getLogger(SysRoleAction.class);

    @Autowired(required = false)
    private MenuAuth menuAuth;
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private SysRoleService sysRoleService;
    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;


    /**
     * 角色视图
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/view")
    public ModelAndView list(HttpServletRequest request) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return RenderView(request, "sysRole/sysRoleView", map);
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
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/pageList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dataPageList(HttpServletResponse response, SysRoleModel model) throws Exception {

        Pager<SysRoleEntity> pager = new Pager<SysRoleEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysRoleEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysRoleEntity.class));
            }
        }

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysRoleEntity.class));
            pager.setSortOperator(sortOper);
        }

        sysRoleService.queryByPage(pager);

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
    public ModelAndView add(HttpServletRequest request) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return RenderView(request, "sysRole/sysRoleAdd", map);
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
    public ModelAndView edit(HttpServletRequest request, @RequestParam Integer id) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        SysRoleEntity entity = sysRoleService.queryById(id);
        map.put("entity", JsonUtils.obj2Json(entity));

        return RenderView(request, "sysRole/sysRoleEdit", map);
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
    public String save(HttpServletRequest request, SysRoleEntity entity) {
        try {
            if (entity.getRoleId() == null) {
                WhereOperator where = new WhereOperator();
                where.addWhere(new QueryWhereBean(Operator.AND, "roleName", Compare.EQ, entity.getRoleName(), SysRoleEntity.class));
                QueryOperator query = new QueryOperator();
                query.setWhereOperator(where);
                SysRoleEntity roleEntity = sysRoleService.queryByOne(query);
                if (roleEntity == null) {
                    entity.setCreateDate(DateUtils.getCurDate());
                    sysRoleService.add(entity);
                } else {
                    return ResultMessage.fail("角色已存在！");
                }
            } else {
                entity.setModifyDate(DateUtils.getCurDate());
                sysRoleService.updateBySelective(entity);
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
            sysRoleService.deleteBatch(id);
            return ResultMessage.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("删除异常");
        }
    }

    /**
     * 修改角色权限视图
     *
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
    @RequestMapping(value = "/modifyRoleAuthView", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyRoleAuthView(@RequestParam Integer roleId) {
        String menusAuths = "[]";
        try {
            menusAuths = sysAdminMgrService.getMenuAuths(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改角色权限视图操作失败");
        }

        return menusAuths;
    }

    /**
     * 保存修改角色权限
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
    @RequestMapping(value = "/modifyRoleAuthSave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyRoleAuthSave(HttpServletRequest request, @RequestParam Integer roleId, @RequestParam String mrs) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Integer>> list = JsonUtils.json2MapsInt(mrs);
            sysAdminMgrService.updateRoleAuth(roleId, list);
            ResultMessage.success(null, map);
        } catch (Exception e) {
            ResultMessage.fail(null, map);
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
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
    @RequestMapping(value = "/updateSeq", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateSeq(HttpServletRequest request, Integer did, Integer seq) {
        try {
            SysRoleEntity entity = new SysRoleEntity();
            entity.setRoleId(did);
            entity.setSeq(seq);
            sysRoleService.updateBySelective(entity);
            return ResultMessage.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("更新失败");
        }
    }
}
