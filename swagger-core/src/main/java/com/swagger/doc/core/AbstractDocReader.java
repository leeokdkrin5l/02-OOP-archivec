package com.swagger.doc.core;

import java.util.Map;

import com.swagger.doc.core.entity.SwaggerDoc;
import com.swagger.doc.core.param.AbstractParamParse;
import com.swagger.doc.core.param.BodyParamParse;
import com.swagger.doc.core.param.PathParamParse;
import com.swagger.doc.core.param.QueryParamParse;
import io.swagger.converter.ModelConverters;
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
    protected final Logger           logger          = LoggerFactory.getLogger(getClass());
    protected Swagger                swagger;
    protected static ModelConverters modelConverters = new ModelConverters();
    protected AbstractParamParse     bodyParse       = new BodyParamParse(modelConverters);
    protected AbstractParamParse     pathParse       = new PathParamParse(modelConverters);
    protected AbstractParamParse     queryParse      = new QueryParamParse(modelConverters);

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
            logger.debug("SwaggerDoc not define use default SwaggerDoc");
        }
        if (swaggerDoc == null) {
            swaggerDoc = new SwaggerDoc.SwaggerDocBuilder().addSkipAnnotations(SessionAttribute.class).build();
        }
        return swaggerDoc;
    }

}
