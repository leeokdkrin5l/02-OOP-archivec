package com.swagger.doc.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-08-05 17:05
 */
public class Swagger  extends io.swagger.models.Swagger {
    @JsonIgnore
    private Map<String, Set<String>> versionPath = new HashMap<>();

    public void addPathVersion(String version, String... path) {
        Set<String> paths = versionPath.get(version);
        if (paths == null) {
            paths = new HashSet<>();
        }
        versionPath.put(version, paths);
        for (String s : path) {
            paths.add(s);
        }
    }

    public Map<String, Set<String>> getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(Map<String, Set<String>> versionPath) {
        this.versionPath = versionPath;
    }
}
