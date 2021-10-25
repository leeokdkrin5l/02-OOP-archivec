package com.swagger.doc.core.entity;

import io.swagger.models.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-14 上午10:06
 */
public class SwaggerDoc {
    private String       doc;
    private List<String> ignoreParamNames;
    private List<Class>  skipAnnotations;
    private List<String> ignoreControllers;
    private Info         info;
    private String       host;
    private String       basePath;
    private List<String> authHeaders;
    private List<String> commonParams;

    public List<String> getCommonParams() {
        return commonParams;
    }

    public void setCommonParams(List<String> commonParams) {
        this.commonParams = commonParams;
    }

    public List<String> getAuthHeaders() {
        return authHeaders;
    }

    public void setAuthHeaders(List<String> authHeaders) {
        this.authHeaders = authHeaders;
    }

    public List<String> getIgnoreControllers() {
        return ignoreControllers;
    }

    public void setIgnoreControllers(List<String> ignoreControllers) {
        this.ignoreControllers = ignoreControllers;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public List<String> getIgnoreParamNames() {
        return ignoreParamNames;
    }

    public void setIgnoreParamNames(List<String> ignoreParamNames) {
        this.ignoreParamNames = ignoreParamNames;
    }

    public List<Class> getSkipAnnotations() {
        return skipAnnotations;
    }

    public void setSkipAnnotations(List<Class> skipAnnotations) {
        this.skipAnnotations = skipAnnotations;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public static final class SwaggerDocBuilder {
        private String       doc;
        private List<String> ignoreParamNames  = new ArrayList<>();
        private List<Class>  skipAnnotations   = new ArrayList<>();
        private List<String> ignoreControllers = new ArrayList<>();
        private Info         info;
        private String       host;
        private String       basePath;
        private Set<String>  authHeaders       = new HashSet<>();
        private Set<String>  commonParams      = new HashSet<>();

        public SwaggerDocBuilder() {
        }

        public SwaggerDocBuilder withDoc(String doc) {
            this.doc = doc;
            return this;
        }
        public SwaggerDocBuilder withCommonParams(String... names) {
            if (names != null) {
                for (String name : names) {
                    this.commonParams.add(name);
                }
            }
            return this;
        }
        public SwaggerDocBuilder withAuthHeader(String... names) {
            if (names != null) {
                for (String name : names) {
                    this.authHeaders.add(name);
                }
            }
            return this;
        }

        public SwaggerDocBuilder addIgnoreParamNames(String... paramNames) {
            if (paramNames != null)
                for (String paramName : paramNames) {
                    this.ignoreParamNames.add(paramName);
                }
            return this;
        }

        public SwaggerDocBuilder addIgnoreControllers(String... controllers) {
            if (controllers != null)
                for (String controller : controllers) {
                    this.ignoreControllers.add(controller);
                }
            return this;
        }

        public SwaggerDocBuilder addSkipAnnotations(Class... skipAnnotations) {
            if (skipAnnotations != null)
                for (Class skipAnnotation : skipAnnotations) {
                    this.skipAnnotations.add(skipAnnotation);
                }
            return this;
        }

        public SwaggerDocBuilder withInfo(Info info) {
            this.info = info;
            return this;
        }

        public SwaggerDocBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public SwaggerDocBuilder withBasePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public SwaggerDoc build() {
            SwaggerDoc swaggerDoc = new SwaggerDoc();
            swaggerDoc.ignoreParamNames = this.ignoreParamNames;
            swaggerDoc.skipAnnotations = this.skipAnnotations;
            swaggerDoc.info = this.info;
            swaggerDoc.basePath = this.basePath;
            swaggerDoc.host = this.host;
            swaggerDoc.doc = this.doc;
            swaggerDoc.ignoreControllers = this.ignoreControllers;
            swaggerDoc.authHeaders = this.authHeaders.stream().collect(Collectors.toList());
            swaggerDoc.commonParams = this.commonParams.stream().collect(Collectors.toList());
            return swaggerDoc;
        }
    }
}
