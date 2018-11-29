package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.MilepostsEntity;
import com.coocaa.pro.manage.mapper.MilepostsMapper;

/**
 * <br>
 * <b>功能：</b>里程碑表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-29 15:19:10<br>
 * <b>详细说明：</b>无<br>
 */
@Service("milepostsService")
public class MilepostsService extends BaseService<MilepostsEntity> {
	
	private final static Logger log= Logger.getLogger(MilepostsService.class);

	@Autowired
    private MilepostsMapper mapper;

		
	public MilepostsMapper getMapper() {
		return mapper;
	}

}
