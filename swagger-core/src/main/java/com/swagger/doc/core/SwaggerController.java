package com.swagger.doc.core;

import com.swagger.doc.core.entity.WrapSwagger;
import com.swagger.doc.core.process.VersionProcess;
import com.swagger.doc.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2017-07-17 下午5:08
 */
@Controller
public class SwaggerController {
    private volatile WrapSwagger wrapSwagger;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SwaggerSourceParse swaggerSourceParse;

    @GetMapping(value = "/swagger/swagger.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String swagger(String v) {
        wrapSwagger = swaggerSourceParse.parseJarSource(applicationContext);
        VersionProcess versionProcess = new VersionProcess();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(VersionProcess.VERSION, v);
        versionProcess.modifySwagger(wrapSwagger, dataMap);
        return JsonUtils.toJson(wrapSwagger);
    }

    @GetMapping(value = "/swagger/ui", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String swaggerUiPage(String v) throws UnsupportedEncodingException {
        String urlEncode = URLEncoder.encode("v=" + v, "utf-8");
        return String.format("redirect:/static/index.html?url=%s", "/swagger/swagger.json?" + urlEncode);
    }

}
