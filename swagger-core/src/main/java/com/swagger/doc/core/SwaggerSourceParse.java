package com.swagger.doc.core;

import com.swagger.doc.core.entity.Swagger;
import com.swagger.doc.core.entity.SwaggerConfigProperties;
import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.FileUtils;
import com.swagger.doc.core.utils.JavaSourceUtils;
import com.swagger.doc.core.utils.SourceReader;
import com.thoughtworks.qdox.model.JavaClass;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-18 上午10:14
 */
public class SwaggerSourceParse {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SwaggerConfigProperties configProperties;
    private static final String DEFAULT_DIR = "source";

    public WrapSwagger parseJarSource(ApplicationContext configurableApplicationContext) {
        String sourceDir = DEFAULT_DIR;
        if (StringUtils.isNoneBlank(configProperties.getSourceDir())) {
            sourceDir = configProperties.getSourceDir();
        }
        File file = null;
        if (configProperties.isUseWar()) {
            try {
                file = new ClassPathResource(sourceDir).getFile();
                logger.info("read file path is {}", file.getAbsolutePath());
            } catch (Exception e) {
                logger.warn("", e);
                file = new File(sourceDir);
                logger.info("read file path is {}", file.getAbsolutePath());

            }
        } else {
            file = new File(sourceDir);
            logger.info("read file path is {}", file.getAbsolutePath());
        }
        File[] files = file.listFiles();
        List<JavaClass> javaClassList = new ArrayList<>();
        if (files != null) {
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    continue;
                }
                try {
                    //如果不是jar文件就直接跳过
                    if (!FileUtils.isJarFile(file1)) {
                        continue;
                    }
                    List<JavaClass> list = SourceReader.readFile(file1);
                    javaClassList.addAll(list);
                } catch (IOException e) {
                    logger.warn("", e);
                    continue;
                }
            }
        }
        List<File> projectFiles = JavaSourceUtils.listProjectJavaFile();
        Map<String, JavaClass> projectJavaClassMap = new HashMap<>();
        try {
            Map<String, JavaClass> javaClassMap = SourceReader
                    .transforJavaClass(SourceReader.readJavaFiles(projectFiles));
            if (javaClassMap != null) {
                projectJavaClassMap.putAll(javaClassMap);
            }
        } catch (IOException e) {
            logger.info("", e);
        }
        Map<String, JavaClass> javaClassMap = SourceReader.transforJavaClass(javaClassList);
        if (javaClassMap != null && javaClassMap.isEmpty()) {
            projectJavaClassMap.putAll(javaClassMap);
        }
        Swagger swagger = new SpringNewDocReader(new Swagger()).read(projectJavaClassMap,
                configurableApplicationContext);
        WrapSwagger wrapSwagger = new WrapSwagger();
        BeanUtils.copyProperties(swagger, wrapSwagger);
        wrapSwagger.process();
        return wrapSwagger;
    }

}
