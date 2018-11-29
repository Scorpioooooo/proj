package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.ProjectsBaseinfoEntity;
import com.coocaa.pro.manage.mapper.ProjectsBaseinfoMapper;

/**
 * <br>
 * <b>功能：</b>基础项目表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-29 15:19:10<br>
 * <b>详细说明：</b>无<br>
 */
@Service("projectsBaseinfoService")
public class ProjectsBaseinfoService extends BaseService<ProjectsBaseinfoEntity> {
	
	private final static Logger log= Logger.getLogger(ProjectsBaseinfoService.class);

	@Autowired
    private ProjectsBaseinfoMapper mapper;

		
	public ProjectsBaseinfoMapper getMapper() {
		return mapper;
	}

}
