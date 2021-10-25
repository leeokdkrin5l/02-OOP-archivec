package com.swagger.doc.core.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-07-31 16:01
 */
public class SwaggerConfigProperties {
    private String  sourceDir;
    private String  basePath;
    private String  host;
    private String  visitPath;
    private boolean useWar = false;

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
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

    public String getVisitPath() {
        return visitPath;
    }

    public void setVisitPath(String visitPath) {
        this.visitPath = visitPath;
    }

    public boolean isUseWar() {
        return useWar;
    }

    public void setUseWar(boolean useWar) {
        this.useWar = useWar;
    }
}
