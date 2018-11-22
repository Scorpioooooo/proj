package com.coocaa.pro.manage.service.sys;

import com.coocaa.fire.video.admin.baseclass.BaseService;
import com.coocaa.fire.video.admin.entity.SysMenuRunEntity;
import com.coocaa.fire.video.admin.mapper.SysMenuRunMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>菜单功能 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:44<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysMenuRunService")
public class SysMenuRunService extends BaseService<SysMenuRunEntity> {
	
	private final static Logger log= Logger.getLogger(SysMenuRunService.class);

	@Autowired(required=false)
    private SysMenuRunMapper mapper;

		
	public SysMenuRunMapper getMapper() {
		return mapper;
	}

}
