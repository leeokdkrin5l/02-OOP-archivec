package com.swagger.doc.core.utils;

import com.swagger.doc.core.entity.RequestMappingInfo;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-29 下午7:28
 */
public class SpringAnnotationUtils {
    private SpringAnnotationUtils() {
    }

    /**
     * 拿到requestMapping INFO
     * @param method
     * @return
     */
    public static RequestMappingInfo getRequestMappingInfo(Method method) {
        RequestMappingInfo requestMappingInfo = null;
        Annotation annotation = method.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            RequestMapping mapping = (RequestMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(mapping.method());
            if (mapping.method().length == 0)
                requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.GET });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        annotation = method.getAnnotation(GetMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            GetMapping mapping = (GetMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.GET });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        annotation = method.getAnnotation(PostMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            PostMapping mapping = (PostMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.POST });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        annotation = method.getAnnotation(PutMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            PutMapping mapping = (PutMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.PUT });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        annotation = method.getAnnotation(DeleteMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            DeleteMapping mapping = (DeleteMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.DELETE });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        annotation = method.getAnnotation(PatchMapping.class);
        if (annotation != null) {
            requestMappingInfo = new RequestMappingInfo();
            PatchMapping mapping = (PatchMapping) annotation;
            requestMappingInfo.setConsumes(mapping.consumes());
            requestMappingInfo.setHeaders(mapping.headers());
            requestMappingInfo.setMethod(new RequestMethod[] { RequestMethod.PATCH });
            requestMappingInfo.setPath(mapping.path());
            requestMappingInfo.setValue(mapping.value());
            requestMappingInfo.setProduces(mapping.produces());
            requestMappingInfo.setName(mapping.name());
        }
        return requestMappingInfo;

    }

    /**
     * 判断是否有RequestBody注解
     * @param parameter
     * @return
     */
    public static boolean isRequestBody(Parameter parameter) {
        return haveAnnotation(parameter, RequestBody.class);
    }

    /**
     * 判断是否是pathParam
     * @param parameter
     * @return
     */
    public static boolean isPathParam(Parameter parameter) {
        return haveAnnotation(parameter, PathVariable.class);
    }

    public static boolean haveAnnotation(Parameter parameter, Class annotationTarget) {
        Annotation[] annotations = parameter.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAssignableFrom(annotationTarget))
                return true;
        }
        return false;
    }

    public static Annotation getFieldAnnotation(Field field, Class annotationTarget) {
        return field.getAnnotation(annotationTarget);
    }

    public static Annotation getAnnotation(Parameter parameter, Class annotationTarget) {
        Annotation[] annotations = parameter.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAssignableFrom(annotationTarget))
                return annotation;
        }
        return null;
    }

    public static String getControllerPath(Class clazz) {
        String path;
        Annotation controllerAnnotation = clazz.getAnnotation(Controller.class);
        path = (String) AnnotationUtils.getValue(controllerAnnotation, "value");
        Annotation requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
        if (requestMapping != null && StringUtils.isEmpty(path)) {
            String[] paths = (String[]) AnnotationUtils.getValue(requestMapping, "path");
            if (paths.length < 0)
                paths = (String[]) AnnotationUtils.getValue(requestMapping, "value");
            if (paths.length > 0)
                path = paths[0];
        }
        return path == null ? "" : path;

    }
}
