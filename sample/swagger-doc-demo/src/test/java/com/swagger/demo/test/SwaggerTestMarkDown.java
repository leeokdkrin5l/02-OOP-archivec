package com.swagger.demo.test;

import com.swagger.demo.SampleApplication;
import com.swagger.doc.core.SwaggerSourceParse;
import com.swagger.doc.core.entity.WrapSwagger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-08-15 下午2:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MockServletContext.class, SampleApplication.class })
@WebAppConfiguration
public class SwaggerTestMarkDown {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SwaggerSourceParse swaggerSourceParse;

    @Test
    public void testGenerateMarkDown() {
//        WrapSwagger wrapSwagger = swaggerSourceParse.parseJarSource(applicationContext);
//        MarkDownUtils.generateMarkDown("wk.md", wrapSwagger);
    }
}
