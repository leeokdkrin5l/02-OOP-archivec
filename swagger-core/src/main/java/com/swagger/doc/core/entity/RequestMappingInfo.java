package com.swagger.doc.core.entity;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-30 下午4:59
 */
public class RequestMappingInfo {
    String          name     = "";

    String[]        value    = new String[] {};

    String[]        path     = new String[] {};

    RequestMethod[] method   = new RequestMethod[] {};

    String[]        params   = new String[] {};

    String[]        headers  = new String[] {};

    String[]        consumes = new String[] {};

    String[]        produces = new String[] {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public RequestMethod[] getMethod() {
        return method;
    }

    public void setMethod(RequestMethod[] method) {
        this.method = method;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public String[] getConsumes() {
        return consumes;
    }

    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }
}
