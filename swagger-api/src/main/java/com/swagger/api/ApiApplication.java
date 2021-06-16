package com.swagger.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-24 下午4:16
 */

@SpringBootApplication(scanBasePackages = {  "com.swagger.api" })
public class ApiApplication extends WebMvcConfigurerAdapter {
    public static final String SOURCE_DIR = "swagger-api/lib/source/";//todo 到时候配置到配置里面去

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
        super.addResourceHandlers(registry);
    }

    public static void main(String args[]) {
        //        for (Method method : RoleController.class.getMethods()) {
        //            System.out.println(SwaggerUtils.typeName(method.getGenericReturnType()));
        //        }
        SpringApplication springApplication = new SpringApplication(ApiApplication.class);
        configurableApplicationContext = springApplication.run(args);

    }

    public static ConfigurableApplicationContext configurableApplicationContext;

}
