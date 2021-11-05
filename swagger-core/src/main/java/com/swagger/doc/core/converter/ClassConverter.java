package com.swagger.doc.core.converter;

import com.thoughtworks.qdox.model.JavaClass;
import io.swagger.models.Path;

import java.util.Map;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/28
 */
public interface ClassConverter {

    /**
     * 统一接口返回路径信息
     *
     * @param controllerJavaClass
     * @param converterContext
     * @return
     */
    Map<String, Path> convertRestApi(JavaClass controllerJavaClass, ConverterContext converterContext);
}
