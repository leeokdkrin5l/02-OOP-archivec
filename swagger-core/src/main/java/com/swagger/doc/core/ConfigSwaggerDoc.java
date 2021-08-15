package com.swagger.doc.core;

import com.swagger.doc.core.properties.ConfigProperties;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午4:13
 */
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ConfigSwaggerDoc implements ImportAware, BeanClassLoaderAware {
    @Autowired
    private ConfigProperties configProperties;
    private ClassLoader      classLoader;

    @Bean
    public SwaggerSourceParse swaggerSourceParse() {
        return new SwaggerSourceParse();
    }

    @Bean
    public SwaggerController swaggerController() {
        return new SwaggerController();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;

    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        Map<String, Object> enableAttrMap = annotationMetadata
            .getAnnotationAttributes(EnableSwaggerDoc.class.getName());
        AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
        if (enableAttrs == null) {
            // search parent classes
            Class<?> currentClass = ClassUtils.resolveClassName(annotationMetadata.getClassName(), classLoader);
            for (Class<?> classToInspect = currentClass; classToInspect != null; classToInspect = classToInspect
                .getSuperclass()) {
                EnableSwaggerDoc enableDemo = AnnotationUtils.findAnnotation(classToInspect, EnableSwaggerDoc.class);
                if (enableDemo == null) {
                    continue;
                }
                enableAttrMap = AnnotationUtils.getAnnotationAttributes(enableDemo);
                enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
            }
        }
        //        this.closeProfiles = enableAttrs.getStringArray("closeProfiles");
    }
}
