package com.swagger.doc.core.param;

import com.swagger.doc.core.utils.SpringAnnotationUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 上午11:50
 */
public abstract class AbstractParamParse {
    protected ModelConverters modelConverters;

    public AbstractParamParse(ModelConverters modelConverters) {
        this.modelConverters = modelConverters;
    }

    /**
     * 解析参数 转换成swagger的parameter
     *
     * @param parameter
     * @return
     */
    public abstract List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod,
                                                   Method method, String modelName, String paramName,
                                                   Map<String, JavaClass> classJavaClassMap);

    protected boolean isRequire(java.lang.reflect.Parameter parameter) {
        Annotation annotation = SpringAnnotationUtils.getAnnotation(parameter, RequestParam.class);
        if (annotation == null) {
            return false;
        }
        RequestParam requestParam = (RequestParam) annotation;
        return requestParam.required();
    }

    protected String getQueryParamName(java.lang.reflect.Parameter parameter) {
        Annotation annotation = SpringAnnotationUtils.getAnnotation(parameter, RequestParam.class);
        if (annotation == null) {
            return null;
        }
        RequestParam requestParam = (RequestParam) annotation;
        if (StringUtils.isNotBlank(requestParam.name())) {
            return requestParam.name();
        }
        return requestParam.value();
    }

    protected String getPathName(java.lang.reflect.Parameter parameter) {
        Annotation annotation = SpringAnnotationUtils.getAnnotation(parameter, PathVariable.class);
        if (annotation == null) {
            return null;
        }
        PathVariable pathVariable = (PathVariable) annotation;
        if (StringUtils.isNotBlank(pathVariable.name())) {
            return pathVariable.name();
        }
        return pathVariable.value();
    }

    public int[] getLength(Field field) {
        Annotation annotation = SpringAnnotationUtils.getFieldAnnotation(field, Length.class);
        if (annotation == null) {
            return null;
        }
        Length length = (Length) annotation;
        int[] lengths = new int[2];
        lengths[0] = length.min();
        lengths[1] = length.max();
        return lengths;
    }
}
