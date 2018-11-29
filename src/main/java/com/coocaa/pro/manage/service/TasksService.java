package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.TasksEntity;
import com.coocaa.pro.manage.mapper.TasksMapper;

/**
 * <br>
 * <b>功能：</b>任务表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-29 15:19:11<br>
 * <b>详细说明：</b>无<br>
 */
@Service("tasksService")
public class TasksService extends BaseService<TasksEntity> {
	
	private final static Logger log= Logger.getLogger(TasksService.class);

	@Autowired
    private TasksMapper mapper;

		
	public TasksMapper getMapper() {
		return mapper;
	}

}
