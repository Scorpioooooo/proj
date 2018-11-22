package com.coocaa.pro.manage.service.sys;

import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.mapper.SysUserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>操作用户 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:45<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysUserService")
public class SysUserService extends BaseService<SysUserEntity> {
	
	private final static Logger log= Logger.getLogger(SysUserService.class);

	@Autowired(required=false)
    private SysUserMapper mapper;

		
	public SysUserMapper getMapper() {
		return mapper;
	}

	public List<String> queryUserRoleEmail(Integer roleid){
		return mapper.queryUserRoleEmail(roleid);
	}
}
