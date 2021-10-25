package com.swagger.doc.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午3:53
 */
@ConfigurationProperties(prefix = "swagger.doc")
public class ConfigProperties {
    private String  sourceDir;
    private String  basePath;
    private String  host;
    private String  visitPath;
    private boolean useWar = false;

    public boolean isUseWar() {
        return useWar;
    }

    public void setUseWar(boolean useWar) {
        this.useWar = useWar;
    }

    public String getVisitPath() {
        return visitPath;
    }

    public void setVisitPath(String visitPath) {
        this.visitPath = visitPath;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
}
