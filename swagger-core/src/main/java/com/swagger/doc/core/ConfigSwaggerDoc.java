package com.swagger.doc.core;

import com.swagger.doc.core.properties.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午4:13
 */
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ConfigSwaggerDoc {
    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public SwaggerController swaggerController() {
        return new SwaggerController();
    }
}
