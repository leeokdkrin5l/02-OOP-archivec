package com.swagger.doc.core.entity;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-31 下午4:56
 */
public class RefEntity {
    private String name;
    private String typeName;
    private String ref;
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
