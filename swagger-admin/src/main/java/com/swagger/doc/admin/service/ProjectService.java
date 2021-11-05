package com.swagger.doc.admin.service;

import com.swagger.doc.core.entity.WrapSwagger;

/**
 * @author kangwang
 * @Description:
 * @date 2020/9/1
 */
public interface ProjectService {
    WrapSwagger findProjectSwagger(Long projectId);
}
