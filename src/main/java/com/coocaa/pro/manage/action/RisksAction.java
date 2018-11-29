package com.coocaa.pro.manage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.AuthEnum.AuthorityEnum;
import com.coocaa.pro.manage.common.MenuAuth;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.common.Constant;


import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.action.BasicAction;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.fire.utils.plugin.Pager;
import com.coocaa.fire.utils.plugin.QueryOperator;
import com.coocaa.fire.utils.plugin.SortOperator;
import com.coocaa.fire.utils.plugin.WhereOperator;

import com.coocaa.fire.utils.plugin.bean.QuerySortBean;
import com.coocaa.fire.utils.plugin.bean.QueryWhereBean;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Compare;
import com.coocaa.fire.utils.plugin.bean.QueryEnums.Operator;
import com.coocaa.pro.manage.service.sys.SysDeptService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.JsonUtils;
import com.coocaa.fire.utils.ReflectHelper;

import com.coocaa.pro.manage.service.RisksService;
import com.coocaa.pro.manage.model.RisksModel;
import com.coocaa.pro.manage.entity.RisksEntity;


@Controller
@Config(menuElId = "risks")//相关配置
@RequestMapping("/risks")
public class RisksAction extends BasicAction{

private final static Logger log=Logger.getLogger(RisksAction.class);

@Autowired(required = false)
private MenuAuth menuAuth;
@Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
private RisksService risksService;
@Autowired(required = false)
private SysRunService sysRunService;
@Autowired(required = false)
private SysAdminMgrService sysAdminMgrService;


/**
 * 风险表视图数据
 *
 * @param request
 * @return
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
@RequestMapping(value = "/view")
@ResponseBody
public String list(HttpServletRequest request)throws Exception{
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("path","risks/risksView");
        return renderToJson(map);
        }

/**
 * 获取菜单运行权限列表
 * @param request
 * @return
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
@RequestMapping(value = "/getMenuInfo", produces = "text/html;charset=UTF-8")
@ResponseBody
public String getMenuInfo(HttpServletRequest request){

        SysUserEntity user=(SysUserEntity)HttpSessionUtils.getSessionValue(request,Constant.SESSION_LOGIN_USER);
        List<SysRunEntity> sysRuns=menuAuth.getMenuTools(this.getClass(),user);

        return renderToJson(sysRuns);
        }

/**
 * 普通查询不分页
 * @param response
 * @param model
 * @return
 * @throws Exception
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
@RequestMapping(value = "/dataList", produces = "text/html;charset=UTF-8")
@ResponseBody
public String dataList(HttpServletResponse response,RisksModel model)throws Exception{

        QueryOperator query=new QueryOperator();
        //查询条件
        WhereOperator whereOperator=new WhereOperator();
        Map<String, Object> map=ReflectHelper.getClassFieldsValues(model);
        for(Map.Entry<String, Object> entry:map.entrySet()){
        whereOperator.addWhere(new QueryWhereBean(Operator.AND,entry.getKey(),
        (entry.getValue()instanceof String?Compare.LIKE:Compare.EQ),entry.getValue(),
        RisksEntity.class));
        }
        query.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper=new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(),model.getOrder(),RisksEntity.class));
            query.setSortOperator(sortOper);
        }
        List<RisksEntity>list=risksService.queryByAll(query);

        return renderToJson(list);
        }

/**
 * 普通查询分页
 * @param response
 * @param model
 * @return
 * @throws Exception
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
@RequestMapping(value = "/pageList", produces = "text/html;charset=UTF-8")
@ResponseBody
public String dataPageList(HttpServletResponse response,RisksModel model)throws Exception{

        Pager<RisksEntity>pager=new Pager<RisksEntity>();
        pager.setPageId(model.getPage());
        pager.setPageSize(model.getRows());
        //查询条件
        WhereOperator whereOperator=new WhereOperator();
        Map<String, Object> map=ReflectHelper.getClassFieldsValues(model);
        for(Map.Entry<String, Object> entry:map.entrySet()){
        whereOperator.addWhere(new QueryWhereBean(Operator.AND,entry.getKey(),
        (entry.getValue()instanceof String?Compare.LIKE:Compare.EQ),entry.getValue(),
        RisksEntity.class));
        }

        pager.setWhereOperator(whereOperator);
        //排序
        if (StringUtils.isNotBlank(model.getSort())) {
            SortOperator sortOper=new SortOperator();
            sortOper.add(new QuerySortBean(model.getSort(),model.getOrder(),RisksEntity.class));
            pager.setSortOperator(sortOper);
        }

        risksService.queryByPage(pager);

        //设置页面数据
        Map<String, Object> jsonMap=new HashMap<String, Object>();
        jsonMap.put("total",pager.getRowCount());
        jsonMap.put("rows",pager.getResults());
        return renderToJson(jsonMap);
        }

/**
 * 增加记录
 * @param request
 * @return
 * @throws Exception
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.ADD)
@RequestMapping("/add")
@ResponseBody
public String add(HttpServletRequest request)throws Exception{
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("path","risks/risksAdd");
        return renderToJson(map);
        }

/**
 * 修改记录
 * @param id
 * @param request
 * @return
 * @throws Exception
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
@RequestMapping("/edit")
@ResponseBody
public String edit(HttpServletRequest request,@RequestParam Integer id)throws Exception{

        Map<String, Object> map=new HashMap<String, Object>();
        RisksEntity entity=risksService.queryById(id);
        map.put("entity",JsonUtils.obj2Json(entity));
        map.put("path","risks/risksEdit");
        return renderToJson(map);
        }

/**
 * 保存
 * @param request
 * @return
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8")
@ResponseBody
public String save(HttpServletRequest request,RisksEntity entity){
        try{
        if(entity.getId()==null){
        risksService.add(entity);
        }else{
        risksService.updateBySelective(entity);
        }
        return ResultMessage.success("数据保存成功");
        }catch(Exception e){
        e.printStackTrace();
        return ResultMessage.fail("数据保存异常");
        }
        }

/**
 * 删除记录
 * @param id
 * @param request
 * @return
 */
@Auth(verifyAuthority = true, authorityType = AuthorityEnum.DELETE)
@RequestMapping(value = "/remove", produces = "text/html;charset=UTF-8")
@ResponseBody
public String remove(Integer[]id,HttpServletRequest request){
        try{
        risksService.deleteBatch(id);
        return ResultMessage.success("删除成功");
        }catch(Exception e){
        e.printStackTrace();
        return ResultMessage.fail("删除异常");
        }
        }

        }
