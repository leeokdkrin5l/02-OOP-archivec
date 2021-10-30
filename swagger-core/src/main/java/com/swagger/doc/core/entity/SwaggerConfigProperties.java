package com.swagger.doc.core.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-07-31 16:01
 */
public class SwaggerConfigProperties {
    /**
     * 源码文件目录
     */
    private String sourceDir;
    private String basePath;
    /**
     * 是否是使用 war包部署 war包部署，会上classpath下面去查找sourceDr
     */
    private boolean useWar = false;

    /**
     * server信息配置，主要用来swagger ui使用
     */

    public boolean isUseWar() {
        return useWar;
    }

    public void setUseWar(boolean useWar) {
        this.useWar = useWar;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
}
