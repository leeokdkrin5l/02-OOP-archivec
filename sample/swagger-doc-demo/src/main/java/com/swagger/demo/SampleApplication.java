package com.swagger.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swagger.doc.core.entity.SwaggerDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:19
 */
@SpringBootApplication
public class SampleApplication extends WebMvcConfigurerAdapter {
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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

    /**
     * 对null进行过滤
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }
}
