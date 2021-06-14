package com.swagger.doc.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import io.swagger.models.Swagger;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-23 下午7:07
 */
public abstract class AbstractDocReader {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    protected Swagger    swagger;

    public abstract Swagger read(Map<String, JavaClass> classJavaClassMap,
                                 ApplicationContext configurableApplicationContext, List<String> ingnoreController);

    public AbstractDocReader(Swagger swagger) {
        this.swagger = swagger;
    }

    public Map<String, JavaClass> transforJavaClass(Set<JavaSource> sources) {
        Map<String, JavaClass> javaClasses = new HashMap<>();
        for (JavaSource source : sources) {
            for (JavaClass javaClass : source.getClasses()) {
                javaClasses.put(javaClass.getFullyQualifiedName(), javaClass);
            }
        }
        return javaClasses;
    }

    public Map<String, JavaAnnotation> getAnnonation(List<JavaAnnotation> annotations) {
        Map<String, JavaAnnotation> result = new HashMap<>();
        for (JavaAnnotation annotation : annotations) {
            result.put(annotation.getType().getFullyQualifiedName(), annotation);
        }
        return result;
    }

    /**
     * 拿到tag对应的 key和value
     * @param docletTags
     * @return
     */
    public Map<String, String> getTags(List<DocletTag> docletTags) {
        Map<String, String> tagsMap = new HashMap<>();
        if (CollectionUtils.isEmpty(docletTags))
            return tagsMap;
        for (DocletTag docletTag : docletTags) {
            tagsMap.put(docletTag.getName(), docletTag.getValue());
        }
        return tagsMap;
    }
}
