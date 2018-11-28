package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.ConfigurationEntity;
import com.coocaa.pro.manage.mapper.ConfigurationMapper;

/**
 * <br>
 * <b>功能：</b>配置表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:44<br>
 * <b>详细说明：</b>无<br>
 */
@Service("configurationService")
public class ConfigurationService extends BaseService<ConfigurationEntity> {
	
	private final static Logger log= Logger.getLogger(ConfigurationService.class);

	@Autowired
    private ConfigurationMapper mapper;

		
	public ConfigurationMapper getMapper() {
		return mapper;
	}

}
