package com.coocaa.pro.manage.service.sys;

import com.coocaa.fire.video.admin.baseclass.BaseService;
import com.coocaa.fire.video.admin.entity.SysUserRoleEntity;
import com.coocaa.fire.video.admin.mapper.SysUserRoleMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <br>
 * <b>功能：</b>用户角色 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:45<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysUserRoleService")
public class SysUserRoleService extends BaseService<SysUserRoleEntity> {
	
	private final static Logger log= Logger.getLogger(SysUserRoleService.class);

	@Autowired(required=false)
    private SysUserRoleMapper mapper;

		
	public SysUserRoleMapper getMapper() {
		return mapper;
	}

}
