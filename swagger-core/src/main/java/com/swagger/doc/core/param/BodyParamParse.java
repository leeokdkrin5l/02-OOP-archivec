package com.swagger.doc.core.param;

import com.swagger.doc.core.utils.JsonUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.converter.ModelConverters;
import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 上午11:57
 */
public class BodyParamParse extends AbstractParamParse {

    public BodyParamParse(ModelConverters modelConverters) {
        super(modelConverters);
    }

    @Override
    public List<Parameter> parseParameter(java.lang.reflect.Parameter parameter, JavaMethod javaMethod, Method method,
                                          String modelName, String paramName,
                                          Map<String, JavaClass> classJavaClassMap) {
        if (StringUtils.isEmpty(modelName))
            throw new IllegalArgumentException(String.format("method %s paramater %s can not use @RequestBody",
                method.getName(), parameter.getName()));

        BodyParameter bodyParameter = new BodyParameter();
        RefModel refModel = new RefModel();
        refModel.set$ref("#/definitions/" + modelName);
        bodyParameter.setName(paramName);
        bodyParameter.setSchema(refModel);
        try {
            Object obj = parameter.getType().newInstance();
            processObj(obj);
            bodyParameter.setDescription(JsonUtils.toNullJson(obj));
        } catch (Exception e) {

        }
        return Arrays.asList(bodyParameter);
    }

    private void processObj(Object obj) {
        getModifyField(obj.getClass()).forEach((k, v) -> {
            //todo 支持判断List 和普通类型，并且采用循环遍历的形式，而且防止死循环 Map的话暂时不做处理
            v.getType();
        });
    }

    public static Map<String, Field> getModifyField(Class clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        Stack<Class> classStack = new Stack<>();
        classStack.push(clazz);
        while (!classStack.isEmpty()) {
            Class tmp = classStack.pop();
            for (Field field : tmp.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
                    continue;
                fieldMap.put(field.getName(), field);

            }
            if (tmp.getSuperclass() != null)
                classStack.push(tmp.getSuperclass());
        }

        return fieldMap;
    }
}
