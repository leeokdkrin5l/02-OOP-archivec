package com.swagger.doc.core;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.utils.JsonUtils;
import com.swagger.doc.core.utils.SwaggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午5:08
 */
@Controller
public class SwaggerController {
    private volatile WrapSwagger wrapSwagger;
    @Autowired
    private ApplicationContext   applicationContext;

    @GetMapping(value = "${swagger.doc.visitPath:/swagger.json}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String swagger() {
        wrapSwagger = SwaggerUtils.parseJarSource("source", applicationContext);
        return JsonUtils.toJson(wrapSwagger);
    }
}
