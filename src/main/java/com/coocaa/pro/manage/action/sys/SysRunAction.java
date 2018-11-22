package com.coocaa.pro.manage.action.sys;

import com.coocaa.admin.annotation.Auth;
import com.coocaa.admin.annotation.Config;
import com.coocaa.admin.bean.AuthEnum.AuthorityEnum;
import com.coocaa.admin.service.sys.SysAdminMgrService;
import com.coocaa.admin.utils.ResultMessage;
import com.coocaa.core.action.BasicAction;
import com.coocaa.core.entity.sys.SysRunEntity;
import com.coocaa.core.model.sys.SysRunModel;
import com.coocaa.core.plugin.Pager;
import com.coocaa.core.plugin.QueryOperator;
import com.coocaa.core.plugin.SortOperator;
import com.coocaa.core.plugin.WhereOperator;
import com.coocaa.core.plugin.bean.QueryEnums.Compare;
import com.coocaa.core.plugin.bean.QueryEnums.Operator;
import com.coocaa.core.plugin.bean.QuerySortBean;
import com.coocaa.core.plugin.bean.QueryWhereBean;
import com.coocaa.core.service.sys.SysRunService;
import com.coocaa.core.utils.JsonUtils;
import com.coocaa.core.utils.ReflectHelper;
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
@Config(menuElId = {"roleConfig"})
@RequestMapping("/sysRun")
public class SysRunAction extends BasicAction{
	
	private final static Logger log= Logger.getLogger(SysRunAction.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private SysRunService sysRunService;
	@Autowired(required=false)
	private SysAdminMgrService sysAdminMgrService;
	
	
	/**
	 * 功能视图
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(verifyAuthority=true,authorityType=AuthorityEnum.BROWSER)
	@RequestMapping(value = "/view")
	public ModelAndView list(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		return RenderView(request, "sysRun/sysRunView", map); 
	}
	
	/**
	 * 普通查询不分页
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyAuthority=true,authorityType=AuthorityEnum.BROWSER)
	@RequestMapping(value = "/dataList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String dataList(HttpServletResponse response, SysRunModel model) throws Exception {
		
		QueryOperator query = new QueryOperator();
		//查询条件
		WhereOperator whereOperator = new WhereOperator();
		Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getValue() instanceof String){
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysRunEntity.class));
			}else{
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysRunEntity.class));
			}
		}
		query.setWhereOperator(whereOperator);
		//排序
		if(StringUtils.isNotBlank(model.getSort())){
			SortOperator sortOper = new SortOperator();
			sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysRunEntity.class));
			query.setSortOperator(sortOper);
		}
		List<SysRunEntity> list  =  sysRunService.queryByAll(query);
		
		return renderToJson(list);
	}
	
	/**
	 * 普通查询分页
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyAuthority=true,authorityType=AuthorityEnum.BROWSER)
	@RequestMapping(value = "/pageList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String dataPageList(HttpServletResponse response, SysRunModel model) throws Exception {
		
		Pager<SysRunEntity> pager = new Pager<SysRunEntity>();
		pager.setPageId(model.getPage());
		pager.setPageSize(model.getRows());
		//查询条件
		WhereOperator whereOperator = new WhereOperator();
		Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getValue() instanceof String){
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysRunEntity.class));
			}else{
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysRunEntity.class));
			}
		}
		
		pager.setWhereOperator(whereOperator);
		//排序
		if(StringUtils.isNotBlank(model.getSort())){
			SortOperator sortOper = new SortOperator();
			sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysRunEntity.class));
			pager.setSortOperator(sortOper);
		}
		
		sysRunService.queryByPage(pager);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",pager.getRowCount());
		jsonMap.put("rows", pager.getResults());
		return renderToJson(jsonMap);
	}
	
	/**
	 * 增加记录
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyAuthority = true, authorityType=AuthorityEnum.ADD)
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		return RenderView(request, "sysRun/sysRunAdd", map);
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
	public ModelAndView edit(HttpServletRequest request, @RequestParam Integer id) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		SysRunEntity entity = sysRunService.queryById(id);
		map.put("entity", JsonUtils.obj2Json(entity));
		
		return RenderView(request, "sysRun/sysRunEdit", map);
	}
	
	/**
	 * 保存
	 * @param id
	 * @param request
	 * @return
	 */
	@Auth(verifyAuthority = true, authorityType = AuthorityEnum.SAVE)
	@RequestMapping(value = "/save",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpServletRequest request, SysRunEntity entity){
		try {
			if(entity.getRunId() == null){
				sysRunService.add(entity);
			}else{
				sysRunService.updateBySelective(entity);
			}
			return ResultMessage.success("数据保存成功");
		} catch (Exception e) {
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
	@RequestMapping(value = "/remove",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String remove(Integer[] id, HttpServletRequest request){
		try {
			sysRunService.deleteBatch(id);
			return ResultMessage.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.fail("删除异常");
		}
	}

}
