package com.coocaa.pro.manage.service.sys;

import com.coocaa.pro.manage.entity.SysLogOpEntity;
import com.coocaa.pro.manage.mapper.SysLogOpMapper;
import com.coocaa.pro.manage.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <br>
 * <b>功能：</b>操作日志 Service<br>
 * <b>作者：</b>siber.xu<br>
 * <b>日期：</b>2014-02-28 17:20:35<br>
 * <b>详细说明：</b>无<br>
 */
@Service("sysLogOpService")
public class SysLogOpService extends BaseService<SysLogOpEntity> {

    private final static Logger log = Logger.getLogger(SysLogOpService.class);

    @Autowired(required = false)
    private SysLogOpMapper mapper;


    public SysLogOpMapper getMapper() {
        return mapper;
    }

}
