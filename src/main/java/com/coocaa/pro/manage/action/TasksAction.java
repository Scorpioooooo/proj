package com.coocaa.pro.manage.action;

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
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.AuthEnum.AuthorityEnum;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.common.MenuAuth;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.entity.TasksEntity;
import com.coocaa.pro.manage.model.TasksModel;
import com.coocaa.pro.manage.service.TaskBaseinfoService;
import com.coocaa.pro.manage.service.TasksService;
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
@Config(menuElId = "tasks")//相关配置
@RequestMapping("/tasks")
public class TasksAction extends BasicAction {

    private final static Logger log = Logger.getLogger(TasksAction.class);

    @Autowired(required = false)
    private MenuAuth menuAuth;
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private TasksService tasksService;

    @Autowired(required = false)
    private TaskBaseinfoService taskBaseinfoService;


    /**
     * 任务表视图数据
     *
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
    @RequestMapping(value = "/view")
    @ResponseBody
    public String list(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "tasks/tasksView");
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
    public String dataList(HttpServletResponse response, TasksModel model) throws Exception {

        QueryOperator query = new QueryOperator();
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(),
                    (entry.getValue() instanceof String ? Compare.LIKE : Compare.EQ), entry.getValue(),
                    TasksEntity.class));
        }
        query.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), TasksEntity.class));
            query.setSortOperator(sortOper);
        }
        List<TasksEntity> list = tasksService.queryByAll(query);

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
    public String dataPageList(HttpServletResponse response, TasksModel model) throws Exception {

        Pager<TasksEntity> pager = new Pager<TasksEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator = new WhereOperator();
        Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(),
                    (entry.getValue() instanceof String ? Compare.LIKE : Compare.EQ), entry.getValue(),
                    TasksEntity.class));
        }

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper = new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), TasksEntity.class));
            pager.setSortOperator(sortOper);
        }

        tasksService.queryByPage(pager);

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
    @ResponseBody
    public String add(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "tasks/tasksAdd");
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
    @ResponseBody
    public String edit(HttpServletRequest request, @RequestParam Integer id) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        TasksEntity entity = tasksService.queryById(id);
        map.put("entity", JsonUtils.obj2Json(entity));
        map.put("path", "tasks/tasksEdit");
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
    public String save(HttpServletRequest request, TasksEntity entity) {
        try {
            if (entity.getId() == null) {
                tasksService.add(entity);
            } else {
                tasksService.updateBySelective(entity);
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
            tasksService.deleteBatch(id);
            return ResultMessage.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("删除异常");
        }
    }

}
