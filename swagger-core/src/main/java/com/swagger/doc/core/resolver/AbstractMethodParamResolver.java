package com.swagger.doc.core.resolver;

import com.swagger.doc.core.utils.CollectionUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/31
 */
public abstract class AbstractMethodParamResolver implements HandlerMethodParamResolver {
    public boolean supportAnnotations(JavaMethod javaMethod,JavaParameter javaParameter, Class ...clazz) {
        boolean res = false;
        List<JavaAnnotation> annotations = javaParameter.getAnnotations();
        Map<String,JavaAnnotation> javaAnnotationMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(annotations)){
            for (JavaAnnotation annotation : annotations) {
                String fullName = annotation.getType().getFullyQualifiedName();
                javaAnnotationMap.put(fullName,annotation);
            }
        }
        for (Class aClass : clazz) {
//            if ()
        }
        return res;
    }
}
