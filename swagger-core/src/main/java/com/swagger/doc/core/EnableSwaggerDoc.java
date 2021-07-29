package com.swagger.doc.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午5:26
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE})
@Documented
@Import({ ConfigSwaggerDoc.class})
@Configuration
public @interface EnableSwaggerDoc {
}
