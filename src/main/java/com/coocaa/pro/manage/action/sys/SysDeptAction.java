package com.coocaa.pro.manage.action.sys;

import com.coocaa.pro.manage.annotation.Config;
import org.slf4j.Logger;
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
@Config(menuElId = "baDept")//相关配置
@RequestMapping("/sysDept")
public class SysDeptAction extends BasicAction{
	
	private final static Logger log= Logger.getLogger(SysDeptAction.class);
	
	@Autowired(required=false)
	private MenuAuth menuAuth;
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private SysDeptService sysDeptService;
	@Autowired(required=false)
	private SysRunService sysRunService;
	@Autowired(required=false)
	private SysAdminMgrService sysAdminMgrService;
	
	
	/**
	 * 部门信息视图
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(verifyAuthority=true,authorityType=AuthorityEnum.BROWSER)
	@RequestMapping(value = "/view")
	public ModelAndView list(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
		map.put("depts", depts);
		
		return RenderView(request, "sysDept/sysDeptView", map); 
	}
	
	/**
	 * 获取菜单运行权限列表
	 * @param request
	 * @param menuId
	 * @return
	 */
	@Auth(verifyAuthority = true, authorityType = AuthorityEnum.BROWSER)
	@RequestMapping(value = "/getMenuInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMenuInfo(HttpServletRequest request){
		
		SysUserEntity user = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
		List<SysRunEntity> sysRuns = menuAuth.getMenuTools(this.getClass(), user);

		return renderToJson(sysRuns);
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
	public String dataPageList(HttpServletResponse response, SysDeptModel model) throws Exception {
		
		Pager<SysDeptEntity> pager = new Pager<SysDeptEntity>();
		pager.setPageId(model.getPage());
		pager.setPageSize(model.getRows());
		//查询条件
		WhereOperator whereOperator = new WhereOperator();
		Map<String, Object> map = ReflectHelper.getClassFieldsValues(model);
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getValue() instanceof String){
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.LIKE, entry.getValue(), SysDeptEntity.class));
			}else{
				whereOperator.addWhere(new QueryWhereBean(Operator.AND, entry.getKey(), Compare.EQ, entry.getValue(), SysDeptEntity.class));
			}
		}
		
		pager.setWhereOperator(whereOperator);
		//排序
		if(StringUtils.isNotBlank(model.getSort())){
			SortOperator sortOper = new SortOperator();
			sortOper.add(new QuerySortBean(model.getSort(), model.getOrder(), SysDeptEntity.class));
			pager.setSortOperator(sortOper);
		}
		
		sysDeptService.queryByPage(pager);
		
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
		List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
		map.put("depts", depts);
		
		return RenderView(request, "sysDept/sysDeptAdd", map);
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
		SysDeptEntity entity = sysDeptService.queryById(id);
		map.put("entity", JsonUtils.obj2Json(entity));
		List<SysDeptEntity> depts = sysDeptService.queryByAll(null);
		map.put("depts", depts);
		
		return RenderView(request, "sysDept/sysDeptEdit", map);
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
	public String save(HttpServletRequest request, SysDeptEntity entity){
		try {
			if(entity.getDeptId() == null){
				sysDeptService.add(entity);
			}else{
				sysDeptService.updateBySelective(entity);
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
			sysDeptService.deleteBatch(id);
			return ResultMessage.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.fail("删除异常");
		}
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
	 *           
	 */
	@Auth(verifyAuthority = true, authorityType = AuthorityEnum.EDIT)
    @RequestMapping(value = "/updateSeq",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String updateSeq(HttpServletRequest request, Integer did, Integer seq){
        try {
        	SysDeptEntity entity = new SysDeptEntity();
            entity.setDeptId(did);
            entity.setSeq(seq);
            sysDeptService.updateBySelective(entity);
            return ResultMessage.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail("更新失败");
        }
    }

}
