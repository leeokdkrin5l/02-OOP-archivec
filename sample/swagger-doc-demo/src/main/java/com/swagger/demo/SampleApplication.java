package com.swagger.demo;

import com.swagger.doc.core.EnableSwaggerDoc;
import com.swagger.doc.core.entity.SwaggerDoc;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:19
 */
@SpringBootApplication
@EnableSwaggerDoc
public class SampleApplication {
    public static void main(String args[]) {
        SpringApplication springApplication = new SpringApplication(SampleApplication.class);
        springApplication.run(args);
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
        Contact contact = new Contact();
        Info info = new Info();
        info.setTitle("测试文档");
        contact.setEmail("542467660@qq.com");
        contact.setName("wk");
        contact.setUrl("http://git.oschina.net/wangkang_daydayup/swagger-doc");
        info.setDescription("swagger-doc解决了springfox用注解污染代码的问题，采用原生java-doc来实现文档的生成，让代码更加干净，学习成本更低");
        info.setContact(contact);
        return new SwaggerDoc.SwaggerDocBuilder().addSkipAnnotations(SessionAttribute.class).withDoc("doc")
            .withDoc("测试文档").withInfo(info).withHost("139.224.35.224")
            .addIgnoreControllers("swaggerController", "basicErrorController").build();
    }

}
