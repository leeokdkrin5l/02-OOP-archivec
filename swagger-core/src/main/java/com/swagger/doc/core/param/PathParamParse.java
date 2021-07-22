package com.swagger.doc.core.param;

import com.swagger.doc.core.utils.JavaSourceUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午1:58
 */
public class PathParamParse extends AbstractParamParse {
    public PathParamParse(ModelConverters modelConverters) {
        super(modelConverters);
    }

    @Override
    public List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod, Method method,
                                          String modelName, String paramName, Map<String, JavaClass> classJavaClassMap) {
        PathParameter pathParameter = new PathParameter();
        pathParameter.setProperty(modelConverters.readAsProperty(parameter.getParameterizedType()));
        if (javaMethod != null) {
            String desc = JavaSourceUtils.readParamDesc(JavaSourceUtils.readJavaMethodParam(javaMethod), paramName);
            pathParameter.setDescription(desc);
        }
        return Arrays.asList(pathParameter);
    }
}
