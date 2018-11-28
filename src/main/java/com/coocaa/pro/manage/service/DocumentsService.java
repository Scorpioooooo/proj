package com.coocaa.pro.manage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coocaa.pro.manage.service.sys.BaseService;

import com.coocaa.pro.manage.entity.DocumentsEntity;
import com.coocaa.pro.manage.mapper.DocumentsMapper;

/**
 * <br>
 * <b>功能：</b>文档管理 Service<br>
 * <b>作者：</b>bean creater<br>
 * <b>日期：</b>2018-11-28 17:44:43<br>
 * <b>详细说明：</b>无<br>
 */
@Service("documentsService")
public class DocumentsService extends BaseService<DocumentsEntity> {
	
	private final static Logger log= Logger.getLogger(DocumentsService.class);

	@Autowired
    private DocumentsMapper mapper;

		
	public DocumentsMapper getMapper() {
		return mapper;
	}

}
