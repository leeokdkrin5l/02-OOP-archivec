package com.swagger.doc.core;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.properties.ConfigProperties;
import com.swagger.doc.core.utils.SourceReader;
import com.thoughtworks.qdox.model.JavaClass;
import io.swagger.models.Swagger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-18 上午10:14
 */
public class SwaggerSourceParse {
    private Logger              logger      = LoggerFactory.getLogger(getClass());
    @Autowired
    private ConfigProperties    configProperties;
    private static final String DEFAULT_DIR = "source";

    public WrapSwagger parseJarSource(ApplicationContext configurableApplicationContext) {
        String sourceDir = DEFAULT_DIR;
        if (StringUtils.isNoneBlank(configProperties.getSourceDir()))
            sourceDir = configProperties.getSourceDir();
        File file = new File(sourceDir);
        File[] files = file.listFiles();
        List<JavaClass> javaClassList = new ArrayList<>();
        if (files != null)
            for (File file1 : files) {
                if (file1.isDirectory())
                    continue;
                try {
                    List<JavaClass> list = SourceReader.readFile(file1);
                    javaClassList.addAll(list);
                } catch (IOException e) {
                    logger.warn("", e);
                    continue;
                }
            }
        Map<String, JavaClass> javaClassMap = SourceReader.transforJavaClass(javaClassList);
        Swagger swagger = new SpringNewDocReader(new Swagger()).read(javaClassMap, configurableApplicationContext);
        WrapSwagger wrapSwagger = new WrapSwagger();
        BeanUtils.copyProperties(swagger, wrapSwagger);
        wrapSwagger.process();
        return wrapSwagger;
    }

}
