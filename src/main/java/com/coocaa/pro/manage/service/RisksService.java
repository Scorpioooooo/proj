package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.RisksEntity;
import com.coocaa.pro.manage.mapper.RisksMapper;

/**
 * <br>
 * <b>功能：</b>风险表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:45<br>
 * <b>详细说明：</b>无<br>
 */
@Service("risksService")
public class RisksService extends BaseService<RisksEntity> {
	
	private final static Logger log= Logger.getLogger(RisksService.class);

	@Autowired
    private RisksMapper mapper;

		
	public RisksMapper getMapper() {
		return mapper;
	}

}
