package com.swagger.api;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.BeanJsonConversionUtil;
import com.swagger.doc.core.utils.SwaggerUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.swagger.api.ApiApplication.SOURCE_DIR;
import static com.swagger.api.ApiApplication.configurableApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-04-28 下午2:44
 */
@Controller
public class AppTestController {
    @RequestMapping(value = "/swagger.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String index() {
        WrapSwagger wrapSwagger = SwaggerUtils.parseJarSource(SOURCE_DIR, configurableApplicationContext,
            Arrays.asList("basicErrorController", "swaggerController", "appTestController"));
        return BeanJsonConversionUtil.beanConversionJson(wrapSwagger);
    }
}
