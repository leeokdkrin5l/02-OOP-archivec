package com.swagger.doc.core.entity;

import io.swagger.models.Operation;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-05-02 下午5:33
 */
public class PathOperation {
    private String    hrefPath;
    private Operation operation;
    private String realPath;

    public String getHrefPath() {
        return hrefPath;
    }

    public void setHrefPath(String hrefPath) {
        this.hrefPath = hrefPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
