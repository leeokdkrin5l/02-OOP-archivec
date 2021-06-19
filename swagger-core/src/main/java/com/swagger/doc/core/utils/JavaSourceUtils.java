package com.swagger.doc.core.utils;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import io.swagger.models.Model;
import io.swagger.models.properties.Property;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.SessionAttribute;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-29 下午7:06
 */
public class JavaSourceUtils {
    public final static String        PARAM       = "param";

    private final static List<String> skipPackage = new ArrayList<>();
    static {
        skipPackage.add("javax.servlet");
        skipPackage.add("org.springframework");
    }

    public static void addSkip(String packageName) {
        skipPackage.add(packageName);
    }

    public static boolean isSkip(Parameter parameter) {
        String packageName = "";
        try {
            if (parameter.getParameterizedType() instanceof Class) {
                Class clazz = (Class) parameter.getParameterizedType();
                packageName = clazz.getPackage().getName();
            } else {
                ParameterizedTypeImpl type = (ParameterizedTypeImpl) parameter.getParameterizedType();
                packageName = type.getRawType().getPackage().getName();
            }
           Annotation[] annotations =  parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof SessionAttribute){
                    return true;
                }
            }
            for (String s : skipPackage) {
                if (packageName.indexOf(s) >= 0)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static Map<String, JavaMethod> getAllMethod(JavaClass javaClass) {
        if (javaClass == null)
            return new HashMap<>();
        return javaClass.getMethods().stream()
            .collect(Collectors.toMap(m -> m.getName(), m -> m, (existingValue, newValue) -> existingValue));

    }

    /**
     * 读取字段描述到mode中
     * @param model
     * @param javaClass
     */
    public static void readClassFieldDoc(Model model, JavaClass javaClass) {
        if (model == null)
            return;
        if (model.getProperties() == null)
            return;
        for (Map.Entry<String, Property> stringPropertyEntry : model.getProperties().entrySet()) {
            JavaField javaField = javaClass.getFieldByName(stringPropertyEntry.getKey());
            if (javaField == null)
                continue;
            stringPropertyEntry.getValue().setDescription(javaField.getComment());
        }
    }

    public static String readParamDesc(Map<String, Map<String, String>> stringMapMap, String param) {
        Map<String, String> paramMap = stringMapMap.get(PARAM);
        String desc = "";
        if (paramMap != null && param != null) {
            desc = paramMap.getOrDefault(param, "");
        }
        return desc;
    }

    public static Map<String, Map<String, String>> readJavaMethodParam(JavaMethod javaMethod) {
        Map<String, Map<String, String>> mapMap = new HashMap<>();
        if (javaMethod != null) {
            if (CollectionUtils.isEmpty(javaMethod.getTags())) {
                return mapMap;
            }
            for (DocletTag docletTag : javaMethod.getTags()) {
                Map<String, String> data = mapMap.get(docletTag.getName());
                if (data == null)
                    data = new HashMap<>();
                mapMap.put(docletTag.getName(), data);
                String value = docletTag.getValue();
                //如果为空 那么就设置key和value都为空
                if (StringUtils.isEmpty(value)) {
                    data.put("", "");
                } else {
                    List<String> map = docletTag.getParameters();
                    String msg = "";
                    for (int i = 1; i < map.size(); i++) {
                        msg += map.get(i);
                    }
                    data.put(map.get(0), msg);
                }
            }
        }
        return mapMap;
    }

}
