package com.swagger.doc.core.param;

import com.swagger.doc.core.utils.JavaSourceUtils;
import com.swagger.doc.core.utils.SpringAnnotationUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Model;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午2:07
 */
public class QueryParamParse extends AbstractParamParse {
    public QueryParamParse(ModelConverters modelConverters) {
        super(modelConverters);
    }

    @Override
    public List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod, Method method,
                                          String modelName, String paramName,
                                          Map<String, JavaClass> classJavaClassMap) {
        List<Parameter> parameterList = new ArrayList<>();
        Map<String, Model> parameterMap = modelConverters.read(parameter.getParameterizedType());
        //如果为空说明是正常的类型 就直接采用基本类型字段
        if (parameterMap.isEmpty()) {
            QueryParameter parameterSwagger = new QueryParameter();
            parameterSwagger.setProperty(modelConverters.readAsProperty(parameter.getParameterizedType()));
            parameterSwagger.setName(paramName);
            if (javaMethod != null) {
                String desc = JavaSourceUtils.readParamDesc(JavaSourceUtils.readJavaMethodParam(javaMethod), paramName);
                parameterSwagger.setDescription(desc);
            }
            if (isRequire(parameter)) {
                parameterSwagger.setRequired(true);
            } else {
                parameterSwagger.setRequired(false);
            }
            String annName = getQueryParamName(parameter);
            if (StringUtils.isNoneBlank(annName)) {
                parameterSwagger.setName(annName);
            }
            parameterList.add(parameterSwagger);

        } else {
            for (Map.Entry<String, Model> modelEntry : parameterMap.entrySet()) {
                modelEntry.getValue().getProperties()
                        .forEach((k, v) -> parseParamMap(k, v, parameterList, classJavaClassMap, parameter));
            }
        }
        return parameterList;
    }

    private void parseParamMap(String name, Property property, List<Parameter> parameterList,
                               Map<String, JavaClass> classJavaClassMap, java.lang.reflect.Parameter parameter) {
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setProperty(property);
        queryParameter.setName(name);
        Class clazz = (Class) parameter.getParameterizedType();
        Field fieldClazz = FieldUtils.getDeclaredField(clazz, name, true);
        JavaClass javaClass = classJavaClassMap.get(((Class) (parameter.getParameterizedType())).getName());
        if (javaClass != null) {
            JavaField field = javaClass.getFieldByName(name);
            Optional.ofNullable(field).ifPresent(o -> queryParameter.setDescription(field.getComment()));
        }
        if (fieldClazz != null) {
            int lengths[] = getLength(fieldClazz);
            if (lengths != null) {
                queryParameter.setMaxLength(lengths[1]);
                queryParameter.setMinLength(lengths[0]);
            }
        }
        parameterList.add(queryParameter);
    }
}
