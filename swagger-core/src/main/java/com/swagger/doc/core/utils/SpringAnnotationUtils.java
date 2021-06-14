package com.swagger.doc.core.utils;

import com.swagger.doc.core.entity.MethodDesc;
import com.swagger.doc.core.entity.RequestMappingInfo;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-29 下午7:28
 */
public class SpringAnnotationUtils {
    private static final Logger          logger         = LoggerFactory.getLogger(SpringAnnotationUtils.class);
    private static final Set<Annotation> ANNOTATION_SET = new HashSet(Arrays.asList(RequestMapping.class,
        GetMapping.class, PostMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class));

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
     * 判断是否是get
     * @param requestMapping
     * @return
     */
    public static boolean isGet(RequestMapping requestMapping) {
        if (requestMapping.method().length == 0)
            return true;
        for (RequestMethod requestMethod : requestMapping.method()) {
            if (requestMethod == RequestMethod.GET)
                return true;
        }
        return false;
    }

    /**
     * 判断是否有RequestBody注解
     * @param parameter
     * @return
     */
    public static boolean isRequestBody(Parameter parameter) {
        return haveAnnotation(parameter, RequestBody.class);
    }

    public static boolean isPathParam(Parameter parameter) {
        return haveAnnotation(parameter, PathVariable.class);
    }

    public static boolean haveAnnotation(Parameter parameter, Class annotationTarget) {
        Annotation annotations[] = parameter.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(annotationTarget.getName()))
                return true;
        }
        return false;
    }

    public static String getControllerPath(Class clazz) {
        String path = "";
        Annotation controllerAnnotation = clazz.getAnnotation(Controller.class);
        path = (String) AnnotationUtils.getValue(controllerAnnotation, "value");
        Annotation requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
        if (requestMapping != null && StringUtils.isEmpty(path)) {
            String paths[] = (String[]) AnnotationUtils.getValue(requestMapping, "path");
            if (paths.length < 0)
                paths = (String[]) AnnotationUtils.getValue(requestMapping, "value");
            if (paths.length > 0)
                path = paths[0];
        }
        return path;

    }

    /**
     *
     * @param clazz
     * @return
     */
    public static List<MethodDesc> getRequestMappingMethod(Class clazz, JavaClass javaClass) {
        List<MethodDesc> methodDescList = new ArrayList<>();
        Map<String, MethodDesc> methodMap = new HashMap<>();
        Method methods[] = clazz.getMethods();
        for (Method method : methods) {
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (requestMapping == null)
                continue;
            MethodDesc methodDesc = new MethodDesc();
            methodDesc.setMethod(method);
            methodDesc.setRequestMapping(requestMapping);
            methodDesc.setName(method.getName());
            //todo 目前暂时不支持多态，同一个方法名字 不同参数的情况不支持
            methodMap.put(method.getName(), methodDesc);
        }
        for (JavaMethod javaMethod : javaClass.getMethods()) {
            String name = javaMethod.getName();
            MethodDesc methodDesc = methodMap.get(name);
            if (methodDesc == null) {
                logger.warn("method {} is not exists", name);
                continue;
            }
            methodDesc.setMethodDesc(javaMethod.getComment());
            methodDesc.setJavaMethod(javaMethod);
            List<DocletTag> docletTagList = javaMethod.getTags();
            if (!CollectionUtils.isEmpty(docletTagList)) {
                for (DocletTag docletTag : docletTagList) {
                    Map<String, Map<String, String>> mapMap = methodDesc.getParamsDesc();
                    Map<String, String> data = mapMap.get(docletTag.getName());
                    if (data == null)
                        data = new HashMap<>();
                    mapMap.put(docletTag.getName(), data);
                    String value = docletTag.getValue();
                    //如果为空 那么就设置key和value都为空
                    if (StringUtils.isEmpty(value)) {
                        data.put("", "");
                    } else {
                        List<String> map = docletTag.getParameters();
                        String msg = "";
                        for (int i = 1; i < map.size(); i++) {
                            msg += map.get(i);
                        }
                        data.put(map.get(0), msg);
                    }
                }
            }
            methodDescList.add(methodDesc);
        }
        return methodDescList;
    }
}
