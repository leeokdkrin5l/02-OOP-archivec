package com.swagger.doc.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午3:53
 */
@ConfigurationProperties(prefix = "swagger.doc")
public class ConfigProperties {
    /**
     * 源码文件目录
     */
    private String sourceDr;
    private String basePath;
    private String visitPath;
    /**
     * 是否是使用 war包部署 war包部署，会上classpath下面去查找sourceDr
     */
    private boolean useWar = false;
    /**
     * server信息配置，主要用来swagger ui使用
     */
    private Server server;

    public static class Server {
        private String host;
        private int port = 8080;
        private boolean https;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isHttps() {
            return https;
        }

        public void setHttps(boolean https) {
            this.https = https;
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

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

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
}
