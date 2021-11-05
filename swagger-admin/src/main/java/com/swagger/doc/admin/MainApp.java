package com.swagger.doc.admin;

import freemarker.core.TemplateElement;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/31
 */
public class MainApp {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File("/Users/kangwang/soft"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template temp = cfg.getTemplate("index.ftl");
        TemplateElement templateElement = temp.getRootTreeNode();
        Map<String,Object> user = new HashMap<String, Object>();
        StringWriter stringWriter = new StringWriter();
        user.put("user","wk");
        temp.process(user, stringWriter);
        System.out.println(stringWriter.getBuffer().toString());
    }
}
