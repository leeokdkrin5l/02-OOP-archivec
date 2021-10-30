package com.swagger.doc.core.process;

import com.swagger.doc.core.entity.WrapSwagger;
import io.swagger.models.Path;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-08-05 16:14
 */
public class VersionProcess implements SwaggerProcess {
    public static final String VERSION = "version";

    @Override
    public WrapSwagger modifySwagger(WrapSwagger wrapSwagger, Map<String, String> data) {
        String version = data.get(VERSION);
        if (StringUtils.isNotBlank(version)) {
            WrapSwagger tmpSwagger = new WrapSwagger();
            BeanUtils.copyProperties(wrapSwagger, tmpSwagger);
            Map<String, Path> pathMap = tmpSwagger.getPaths();
            Set<String> pathSet = tmpSwagger.getVersionPath().get(version);
            if (pathSet != null) {
                for (String path : pathSet) {
                    pathMap.remove(path);
                }
                tmpSwagger.setPaths(pathMap);
            }

        }
        return wrapSwagger;
    }
}
