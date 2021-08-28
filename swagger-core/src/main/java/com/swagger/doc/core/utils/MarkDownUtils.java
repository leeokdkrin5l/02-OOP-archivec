package com.swagger.doc.core.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.swagger.doc.core.entity.WrapSwagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-08-15 下午2:40
 */
public class MarkDownUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkDownUtils.class);

    public static void generateMarkDown(String path, WrapSwagger wrapSwagger) {
        File file2 = new File(path);
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("");
            loader.setSuffix(".hbs");
            Handlebars handlebars = new Handlebars(loader);
            initHandlebars(handlebars);
            Template template = handlebars.compile("markdown");
            String content = template.apply(wrapSwagger);
            FileWriter fileWriter1 = new FileWriter(file2);
            fileWriter1.write(content);
            fileWriter1.close();
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    private static void initHandlebars(Handlebars handlebars) {
        handlebars.registerHelper("ifeq", (Helper<String>) (value, options) -> {
            if (value == null || options.param(0) == null) {
                return options.inverse();
            }
            if (value.equals(options.param(0))) {
                return options.fn();
            }
            return options.inverse();
        });

        handlebars.registerHelper("basename", (Helper<String>) (value, options) -> {
            if (value == null) {
                return null;
            }
            int lastSlash = value.lastIndexOf("/");
            if (lastSlash == -1) {
                return value;
            } else {
                return value.substring(lastSlash + 1);
            }
        });
        handlebars.registerHelper("lowercase", (Helper<String>) (value, options) -> {

            if (value == null) {
                return null;
            }
            int lastSlash = value.lastIndexOf("/");
            if (lastSlash == -1) {
                return value;
            } else {
                return value.substring(lastSlash + 1).toLowerCase();
            }
        });
        handlebars.registerHelper("emptyif", (Helper<String>) (value, options) -> {

            if (value == null || value.equals("")) {
                return "无";
            }
            return value;
        });
        handlebars.registerHelper(StringHelpers.join.name(), StringHelpers.join);
        handlebars.registerHelper(StringHelpers.lower.name(), StringHelpers.lower);

    }
}
