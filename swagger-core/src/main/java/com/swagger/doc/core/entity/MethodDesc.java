package com.swagger.doc.core.entity;

import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.models.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-24 下午4:58
 */
public class MethodDesc extends BaseEntity {
    private String                           parentPath;
    private String                           name;
    private Method                           method;
    private JavaMethod                       javaMethod;
    private List<ParamsDesc>                 params;
    private RequestMapping                   requestMapping;
    private RequestMappingInfo               requestMappingInfo;
    private Map<String, Map<String, String>> paramsDesc = new HashMap<>();
    private String                           methodDesc;
    private Map<String, Model>               modelMap   = new HashMap<>();

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public RequestMappingInfo getRequestMappingInfo() {
        return requestMappingInfo;
    }

    public void setRequestMappingInfo(RequestMappingInfo requestMappingInfo) {
        this.requestMappingInfo = requestMappingInfo;
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public void setModelMap(Map<String, Model> modelMap) {
        this.modelMap = modelMap;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public Map<String, Map<String, String>> getParamsDesc() {
        return paramsDesc;
    }

    public void setParamsDesc(Map<String, Map<String, String>> paramsDesc) {
        this.paramsDesc = paramsDesc;
    }

    public JavaMethod getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(JavaMethod javaMethod) {
        this.javaMethod = javaMethod;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParamsDesc> getParams() {
        return params;
    }

    public void setParams(List<ParamsDesc> params) {
        this.params = params;
    }

}
