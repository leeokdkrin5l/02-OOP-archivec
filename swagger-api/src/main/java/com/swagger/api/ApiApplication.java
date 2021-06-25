package com.swagger.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-24 下午4:16
 */

@SpringBootApplication(scanBasePackages = { "com.swagger.api" })
public class ApiApplication extends WebMvcConfigurerAdapter {
    public static final String SOURCE_DIR = "swagger-api/lib/source/";//todo 到时候配置到配置里面去

    public static void main(String args[]) {
        SpringApplication springApplication = new SpringApplication(ApiApplication.class);
        configurableApplicationContext = springApplication.run(args);

    }

    public static ConfigurableApplicationContext configurableApplicationContext;

}
