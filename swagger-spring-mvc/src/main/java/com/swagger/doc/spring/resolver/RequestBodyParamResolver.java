package com.swagger.doc.spring.resolver;

import com.swagger.doc.core.resolver.AbstractMethodParamResolver;
import com.swagger.doc.core.resolver.HandlerMethodParamResolver;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import io.swagger.models.parameters.Parameter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author kangwang
 * @Description:
 * @RequestBody 解析器
 * @date 2020/8/31
 */
public class RequestBodyParamResolver extends AbstractMethodParamResolver implements HandlerMethodParamResolver {
    @Override
    public boolean supportsParameter(JavaMethod javaMethod, JavaParameter javaParameter) {
        return supportAnnotations(javaMethod, javaParameter, RequestBody.class);
    }

    @Override
    public List<Parameter> resolveArgument(JavaMethod javaMethod, JavaParameter javaParameter) {
        return null;
    }
}
