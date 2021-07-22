package com.swagger.doc.core.param;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.parameters.Parameter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 上午11:50
 */
public abstract class AbstractParamParse {
    protected ModelConverters modelConverters;

    public AbstractParamParse(ModelConverters modelConverters) {
        this.modelConverters = new ModelConverters();
    }

    /**
     * 解析参数 转换成swagger的parameter
     * @param parameter
     * @return
     */
    public abstract List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod,
                                                   Method method, String modelName, String paramName,
                                                   Map<String, JavaClass> classJavaClassMap);
}
