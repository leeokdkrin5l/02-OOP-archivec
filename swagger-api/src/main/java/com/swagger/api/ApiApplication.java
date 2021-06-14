package com.swagger.api;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.swagger.doc.core.SpringNewDocReader;
import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.BeanJsonConversionUtil;
import com.swagger.doc.core.utils.SourceReader;
import com.swagger.doc.core.utils.SwaggerUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.xiaojukeji.sec.upm.ops.api.controller.RoleController;
import io.swagger.models.Swagger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-24 下午4:16
 */

@SpringBootApplication(scanBasePackages = { "com.xiaojukeji.sec.upm.ops.api", "com.swagger.api" })
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
