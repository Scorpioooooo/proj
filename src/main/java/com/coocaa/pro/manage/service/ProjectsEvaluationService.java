package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coocaa.pro.manage.entity.ProjectsEvaluationEntity;
import com.coocaa.pro.manage.mapper.ProjectsEvaluationMapper;

/**
 * <br>
 * <b>功能：</b>项目评价表 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-29 15:19:10<br>
 * <b>详细说明：</b>无<br>
 */
@Service("projectsEvaluationService")
public class ProjectsEvaluationService extends BaseService<ProjectsEvaluationEntity> {

    private final static Logger log = Logger.getLogger(ProjectsEvaluationService.class);

    @Autowired
    private ProjectsEvaluationMapper mapper;


    public ProjectsEvaluationMapper getMapper() {
        return mapper;
    }

}
