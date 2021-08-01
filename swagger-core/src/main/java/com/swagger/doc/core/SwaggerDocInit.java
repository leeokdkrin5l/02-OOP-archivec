package com.swagger.doc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午6:28
 */
@Configuration
public class SwaggerDocInit {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        logger.info("use swagger please use @EnableSwaggerDoc");
    }
}
