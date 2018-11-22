package com.coocaa.pro.manage.mapper;


import com.coocaa.pro.manage.entity.SysRunEntity;

import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>功能：</b>功能 Mapper<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
public interface SysRunMapper extends BaseMapper<SysRunEntity> {
	
	/**
	 * 获取菜单运行权限列表
	 * @param map key menuId roles-List(int) tools==true只需工具栏 
	 * @return
	 */
	public List<SysRunEntity> queryMenuRunsByMenuId(Map<String, Object> map);
	
	/**
	 * 获取菜单运行列表
	 * @param map key menuId tools==true只需工具栏 
	 * @return
	 */
	public List<SysRunEntity> queryMenuRuns(Map<String, Object> map);
	
	/**
	 * 获取菜单没有操作列表
	 * @param menuId
	 * @return
	 */
	public List<SysRunEntity> getNotRunByMenuId(Integer menuId);
	
	/**
	 * 获取菜单已有操作列表
	 * @param menuId
	 * @return
	 */
	public List<SysRunEntity> getRunByMenuId(Integer menuId);
}
