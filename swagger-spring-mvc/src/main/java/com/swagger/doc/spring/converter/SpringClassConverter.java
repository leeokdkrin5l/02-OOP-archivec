package com.swagger.doc.spring.converter;

import com.swagger.doc.core.converter.BaseClassConverter;
import com.swagger.doc.core.converter.ClassConverter;
import com.swagger.doc.core.converter.ConverterContext;
import com.swagger.doc.core.utils.JavaSourceUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.models.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * spring 相关
 *
 * @author kangwang
 * @Description:
 * @date 2020/8/28
 */
public class SpringClassConverter extends BaseClassConverter implements ClassConverter {

    @Override
    public Map<String, Path> convertRestApi(JavaClass controllerJavaClass, ConverterContext converterContext) {
        Map<String, Path> pathMap = new HashMap<String, Path>();
        JavaAnnotation javaAnnotation = null;
        Map<String, JavaMethod> javaMethodMap = JavaSourceUtils.getAllMethod(controllerJavaClass);
        for (Map.Entry<String, JavaMethod> entry : javaMethodMap.entrySet()) {
//            entry.getValue().getParameterByName("123").getType();
        }
        return null;
    }
}
