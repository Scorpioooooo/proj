package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.TaskBaseinfoEntity;
import com.coocaa.pro.manage.mapper.TaskBaseinfoMapper;

/**
 * <br>
 * <b>功能：</b>基础任务表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:46<br>
 * <b>详细说明：</b>无<br>
 */
@Service("taskBaseinfoService")
public class TaskBaseinfoService extends BaseService<TaskBaseinfoEntity> {
	
	private final static Logger log= Logger.getLogger(TaskBaseinfoService.class);

	@Autowired
    private TaskBaseinfoMapper mapper;

		
	public TaskBaseinfoMapper getMapper() {
		return mapper;
	}

}
