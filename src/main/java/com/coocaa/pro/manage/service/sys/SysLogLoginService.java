package com.coocaa.pro.manage.service.sys;

import com.coocaa.pro.manage.entity.SysLogLoginEntity;
import com.coocaa.pro.manage.mapper.SysLogLoginMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <br>
 * <b>功能：</b>登录日志 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-28 17:20:35<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysLogLoginService")
public class SysLogLoginService extends BaseService<SysLogLoginEntity> {
	
	private final static Logger log= Logger.getLogger(SysLogLoginService.class);

	@Autowired(required=false)
    private SysLogLoginMapper mapper;

		
	public SysLogLoginMapper getMapper() {
		return mapper;
	}

}
