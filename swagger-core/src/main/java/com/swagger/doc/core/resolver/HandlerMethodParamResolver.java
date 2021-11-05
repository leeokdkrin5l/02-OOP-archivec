package com.swagger.doc.core.resolver;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import io.swagger.models.parameters.Parameter;

import java.util.List;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/28
 */
public interface HandlerMethodParamResolver {
    /**
     * 判断是否支持
     *
     * @param javaMethod
     * @return
     */
    boolean supportsParameter(JavaMethod javaMethod, JavaParameter javaParameter);

    List<Parameter> resolveArgument(JavaMethod javaMethod, JavaParameter javaParameter);

}
