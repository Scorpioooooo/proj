package com.coocaa.pro.manage.service.sys;


import com.coocaa.fire.video.admin.baseclass.BaseService;
import com.coocaa.fire.video.admin.entity.SysRoleEntity;
import com.coocaa.fire.video.admin.entity.SysUserRoleEntity;
import com.coocaa.fire.video.admin.mapper.SysRoleMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>角色 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysRoleService")
public class SysRoleService extends BaseService<SysRoleEntity> {
	
	private final static Logger log= Logger.getLogger(SysRoleService.class);

	@Autowired(required=false)
    private SysRoleMapper mapper;

	@Autowired
	private SysRoleMenuRunService sysRoleMenuRunService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
		
	@Override
	public void delete(Object... ids) throws Exception {
		
		//删除角色时，同时删除角色权限表
		sysRoleMenuRunService.delete(ids);
		for (Object object : ids) {
			SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
			userRoleEntity.setRoleId((Integer)object);
			sysUserRoleService.deleteBatch(userRoleEntity);
		}
		super.delete(ids);
	}

	@Override
	public Integer deleteBatch(Object[] ids) throws Exception {
		
		//删除角色时，同时删除角色权限表
		sysRoleMenuRunService.delete(ids);
		for (Object object : ids) {
			SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
			userRoleEntity.setRoleId((Integer)object);
			sysUserRoleService.deleteBatch(userRoleEntity);
		}
		return super.deleteBatch(ids);
	}
	
	/**
	 * 获取用户没有角色列表
	 * @param userId
	 * @return
	 */
	public List<SysRoleEntity> getNotRolesByUserId(Integer userId){
		
		return mapper.getNotRolesByUserId(userId);
	}
	
	/**
	 * 获取用户已有角色列表
	 * @param userId
	 * @return
	 */
	public List<SysRoleEntity> getRolesByUserId(Integer userId){
		
		return mapper.getRolesByUserId(userId);
	}

	public SysRoleMapper getMapper() {
		return mapper;
	}

}
