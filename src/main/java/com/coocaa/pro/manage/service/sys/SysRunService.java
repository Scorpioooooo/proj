package com.coocaa.pro.manage.service.sys;

import com.coocaa.fire.utils.cached.CacheKeyUtils;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.mapper.SysRunMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>功能：</b>功能 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysRunService")
public class SysRunService extends BaseService<SysRunEntity> {
	
	private final static Logger log= Logger.getLogger(SysRunService.class);

	@Autowired(required=false)
    private SysRunMapper mapper;

	/**
	 * 获取菜单运行权限列表
	 * @param menuId
	 * @param roles
	 * @param tools true-权限工具栏
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysRunEntity> queryMenuRunsByMenuId(Integer menuId, List<Integer> roles, Boolean tools){
		String key = CacheKeyUtils.getCacheKeyList("menu_run_" + menuId + "_" + (tools == true ? "1" : "0"), roles);
		//List<SysRunEntity> sysRuns = (List<SysRunEntity>) MemcachedCMgr.getInstance().get(key);
		List<SysRunEntity> sysRuns = null;
		if(sysRuns == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menuId", menuId);
			map.put("roles", roles);
			map.put("tools", tools);
			sysRuns = mapper.queryMenuRunsByMenuId(map);
			//MemcachedCMgr.getInstance().set(key, CacheEnum.CacheTimeEnum.CACHE_EXP_H.key, sysRuns);
		}
		return sysRuns;
	}
	
	/**
	 * 获取菜单运行列表
	 * @param menuId
	 * @param tools true-权限为工具栏
	 * @return
	 */
	public List<SysRunEntity> queryMenuRuns(Integer menuId, Boolean tools){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuId", menuId);
		map.put("tools", tools);
		return mapper.queryMenuRuns(map);
	}
	
	/**
	 * 获取菜单没有操作列表
	 * @param menuId
	 * @return
	 */
	public List<SysRunEntity> getNotRunByMenuId(Integer menuId){
		
		return mapper.getNotRunByMenuId(menuId);
	}
	
	/**
	 * 获取菜单已有操作列表
	 * @param menuId
	 * @return
	 */
	public List<SysRunEntity> getRunByMenuId(Integer menuId){
		
		return mapper.getRunByMenuId(menuId);
	}
		
	public SysRunMapper getMapper() {
		return mapper;
	}

}
