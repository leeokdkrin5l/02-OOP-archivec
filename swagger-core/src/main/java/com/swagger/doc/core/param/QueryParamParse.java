package com.swagger.doc.core.param;

import com.swagger.doc.core.utils.JavaSourceUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Model;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.Property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            parameterList.add(parameterSwagger);
        } else {
            for (Map.Entry<String, Model> modelEntry : parameterMap.entrySet()) {
                for (Map.Entry<String, Property> stringPropertyEntry : modelEntry.getValue().getProperties()
                    .entrySet()) {
                    QueryParameter queryParameter = new QueryParameter();
                    queryParameter.setProperty(stringPropertyEntry.getValue());
                    queryParameter.setName(stringPropertyEntry.getKey());
                    JavaClass javaClass = classJavaClassMap.get(((Class) (parameter.getParameterizedType())).getName());
                    if (javaClass != null) {
                        JavaField field = javaClass.getFieldByName(stringPropertyEntry.getKey());
                        if (field != null)
                            queryParameter.setDescription(field.getComment());
                    }
                    parameterList.add(queryParameter);

                }
            }
        }
        return parameterList;
    }
}
