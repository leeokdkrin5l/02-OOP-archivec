package com.swagger.doc.core.process;

import com.swagger.doc.core.entity.WrapSwagger;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-08-05 16:14
 */
public interface SwaggerProcess {
    /**
     * 修改swagger 数据
     *
     * @param wrapSwagger
     * @return
     */
    WrapSwagger modifySwagger(WrapSwagger wrapSwagger, Map<String, String> data);
}
