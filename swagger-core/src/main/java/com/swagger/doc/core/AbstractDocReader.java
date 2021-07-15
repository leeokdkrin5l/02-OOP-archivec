package com.swagger.doc.core;

import java.util.Map;

import com.swagger.doc.core.entity.SwaggerDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.thoughtworks.qdox.model.JavaClass;

import io.swagger.models.Swagger;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-23 下午7:07
 */
public abstract class AbstractDocReader {
    protected final Logger           logger = LoggerFactory.getLogger(getClass());
    protected Swagger                swagger;
    protected Map<String, JavaClass> javaClassMap;

    public abstract Swagger read(Map<String, JavaClass> classJavaClassMap,
                                 ApplicationContext configurableApplicationContext);

    public AbstractDocReader(Swagger swagger) {
        this.swagger = swagger;
    }

    protected SwaggerDoc getSwaggerDoc(ApplicationContext applicationContext) {
        SwaggerDoc swaggerDoc = null;
        try {
            swaggerDoc = applicationContext.getBean(SwaggerDoc.class);
        } catch (NoSuchBeanDefinitionException e) {
        }
        if (swaggerDoc == null) {
            swaggerDoc = new SwaggerDoc.SwaggerDocBuilder().addSkipAnnotations(SessionAttribute.class).build();
        }
        return swaggerDoc;
    }

}
