package com.swagger.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.SwaggerUtils;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:26
 */
@RestController
public class SwaggerController {
    private volatile WrapSwagger wrapSwagger;

    @GetMapping(value = "/swagger.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WrapSwagger wrapSwagger() {
        wrapSwagger = SwaggerUtils.parseJarSource("source", SampleApplication.getInstalce());
        return wrapSwagger;
    }

}
