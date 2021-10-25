package com.swagger.doc.starter;

import com.swagger.doc.core.SwaggerController;
import com.swagger.doc.core.SwaggerSourceParse;
import com.swagger.doc.core.entity.SwaggerConfigProperties;
import com.swagger.doc.starter.properties.ConfigProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
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
    private ClassLoader classLoader;

    @Bean
    public SwaggerSourceParse swaggerSourceParse() {
        return new SwaggerSourceParse();
    }

    @Bean
    public SwaggerController swaggerController() {
        return new SwaggerController();
    }


    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;

    }

    @Bean
    public SwaggerConfigProperties swaggerConfigProperties(ConfigProperties configProperties) {
        SwaggerConfigProperties swaggerConfigProperties = new SwaggerConfigProperties();
        BeanUtils.copyProperties(configProperties, swaggerConfigProperties);
        return swaggerConfigProperties;
    }

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
    }
}
