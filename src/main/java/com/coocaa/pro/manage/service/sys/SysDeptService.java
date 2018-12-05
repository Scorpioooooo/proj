package com.coocaa.pro.manage.service.sys;

import com.coocaa.pro.manage.entity.SysDeptEntity;
import com.coocaa.pro.manage.mapper.SysDeptMapper;
import com.coocaa.pro.manage.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <br>
 * <b>功能：</b>部门信息 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-20 15:06:43<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysDeptService")
public class SysDeptService extends BaseService<SysDeptEntity> {

    private final static Logger log = Logger.getLogger(SysDeptService.class);

    @Autowired(required = false)
    private SysDeptMapper mapper;


    public SysDeptMapper getMapper() {
        return mapper;
    }

}
