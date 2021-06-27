package com.swagger.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:19
 */
@SpringBootApplication
public class SampleApplication  {
    private static ConfigurableApplicationContext configurableApplicationContext;
    public static void main(String args[]){
        SpringApplication springApplication = new SpringApplication(SampleApplication.class);
        configurableApplicationContext = springApplication.run(args);

    }
    public static ConfigurableApplicationContext getInstalce() {
        return configurableApplicationContext;
    }
}
