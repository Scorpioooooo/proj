package com.coocaa.pro.manage.action.sys;


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
import com.coocaa.pro.manage.entity.SysLogOpEntity;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.model.SysLogOpModel;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysLogOpService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Config(menuElId = "sysLogOp")//相关配置
@RequestMapping("/sysLogOp")
public class SysLogOpAction extends BasicAction {

    private final static Logger log = Logger.getLogger(SysLogOpAction.class);

    @Autowired(required = false)
    private MenuAuth menuAuth;
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private SysLogOpService sysLogOpService;
    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;


    /**
     * 操作日志视图
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/view")
    public String list(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "sysLogOp/sysLogOpView");
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
    public String dataList(HttpServletResponse response, SysLogOpModel model) throws Exception {

        QueryOperator query = new QueryOperator();
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysLogOpEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysLogOpEntity.class));
            }
        }
        query.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysLogOpEntity.class));
            query.setSortOperator(sortOper);
        }
        List<SysLogOpEntity> list = sysLogOpService.queryByAll(query);

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
    public String dataPageList(HttpServletResponse response, SysLogOpModel model) throws Exception {
        if (model.getSort() == null) {
            model.setSort("createTime");
            model.setOrder("DESC");
        }

        Pager<SysLogOpEntity> pager = new Pager<SysLogOpEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysLogOpEntity.class));
            } else {
                whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysLogOpEntity.class));
            }
        }

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysLogOpEntity.class));
            pager.setSortOperator(sortOper);
        }

        sysLogOpService.queryByPage(pager);

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
        map.put("path", "sysLogOp/sysLogOpAdd");
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
        SysLogOpEntity entity = sysLogOpService.queryById(id);
        map.put("entity", JsonUtils.obj2Json(entity));
        map.put("path", "sysLogOp/sysLogOpEdit");
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
    public String save(HttpServletRequest request, SysLogOpEntity entity) {
        try {
            if (entity.getId() == null) {
                sysLogOpService.add(entity);
            } else {
                sysLogOpService.updateBySelective(entity);
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
            sysLogOpService.deleteBatch(id);
            return ResultMessage.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("删除异常");
        }
    }

}
