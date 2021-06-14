package com.swagger.api;

import com.xiaojukeji.sec.upm.ops.api.interceptor.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-04-20 上午10:54
 */
@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {

    @Bean

    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)

            .apiInfo(apiInfo());



    }
    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new FrontFilter());
        filterRegistrationBean.setName("logFilterRegistrationBean");
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()

            .title("swagger-bootstrap-ui RESTful APIs")

            .description("swagger-bootstrap-ui")

            .termsOfServiceUrl("http://api.test.com/")

            .contact("developer@mail.com")

            .version("1.0")

            .build();

    }

}
