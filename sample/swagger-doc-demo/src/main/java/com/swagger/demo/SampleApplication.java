package com.swagger.demo;

import com.swagger.doc.core.entity.SwaggerDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:19
 */
@SpringBootApplication
public class SampleApplication extends WebMvcConfigurationSupport {
    private static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String args[]) {
        SpringApplication springApplication = new SpringApplication(SampleApplication.class);
        configurableApplicationContext = springApplication.run(args);

    }

    public static ConfigurableApplicationContext getInstalce() {
        return configurableApplicationContext;
    }

    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new FilterInterceptor());
        filterRegistrationBean.setName("logFilterRegistrationBean");
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public SwaggerDoc swaggerDoc() {
        return new SwaggerDoc.SwaggerDocBuilder().addSkipAnnotations(SessionAttribute.class).withDoc("doc")
            .withHost("139.224.35.224").addIgnoreControllers("swaggerController").build();
    }
}
