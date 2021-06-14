package com.swagger.doc.core.entity;

import com.thoughtworks.qdox.model.JavaClass;

import java.util.List;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-24 下午4:57
 */
public class ControllerDesc extends BaseEntity {
    private Class            aClass;
    private JavaClass        javaClass;
    private String           name;
    private String           desc;
    private List<MethodDesc> methodDesc;
    private String           parentPath;

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<MethodDesc> getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(List<MethodDesc> methodDesc) {
        this.methodDesc = methodDesc;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
