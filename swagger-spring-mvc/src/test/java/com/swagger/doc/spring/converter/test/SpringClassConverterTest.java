package com.swagger.doc.spring.converter.test;

import com.swagger.doc.core.converter.ConverterContext;
import com.swagger.doc.core.utils.SourceReader;
import com.swagger.doc.spring.converter.SpringClassConverter;
import com.swagger.doc.spring.resolver.RequestBodyParamResolver;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/28
 */
public class SpringClassConverterTest {
    public static void main(String[] args) throws IOException {
        ConverterContext converterContext = new ConverterContext();
        List<JavaClass> list = SourceReader.readJavaFiles(Arrays.asList(new File("/Users/kan" +
                "gwang/workspace/own/swagger-doc/sample/swagger-doc-demo/src/main/java/com/swagger/demo/IndexController.java")));

        SpringClassConverter springClassConverter = new SpringClassConverter();
        springClassConverter.convertRestApi(list.get(0), converterContext);
        RequestBodyParamResolver requestBodyParamResolver = new RequestBodyParamResolver();
        for (JavaClass javaClass : list) {
            for (JavaMethod method : javaClass.getMethods()) {
                for (JavaParameter parameter : method.getParameters()) {
                    requestBodyParamResolver.supportsParameter(method, parameter);
                }
            }
        }
    }
}
