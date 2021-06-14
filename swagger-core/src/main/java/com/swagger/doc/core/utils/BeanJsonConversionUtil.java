package com.swagger.doc.core.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 Created by IntelliJ IDEA.
 User: wk
 Date: 2017-03-22 上午10:53
 */
public class BeanJsonConversionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanJsonConversionUtil.class);

    private BeanJsonConversionUtil() {
    }

    private static ObjectMapper objectMapper        = new ObjectMapper();

    private static ObjectMapper objectMapperOpenApi = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 对象转化成json字符串
     *
     * @param object
     * @return
     */
    public static String beanConversionJson(Object object) {
        if (object != null) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                LOGGER.warn("JsonProcessingException", e);
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * json字符串转化成对象
     *
     * @param tClass
     * @return
     */
    public static <T> T jsonConversionBean(String jsonStr, Class<T> tClass) {
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                return objectMapper.readValue(jsonStr, tClass);
            } catch (Exception e) {
                LOGGER.warn("jsonConversionBean Error", e);
            }
        }
        return null;
    }

    /**
     * json字符串转化成对象集合
     *
     * @param tClass
     * @return
     */
    public static <T> List<T> jsonConversionBeanArray(String jsonStr, Class<T> tClass) {
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                return objectMapper.readValue(jsonStr,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
            } catch (Exception e) {
                LOGGER.warn("jsonConversionBeanArray Error", e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * json字符串转化成 Map
     *
     * @param jsonStr
     * @param kClass
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> jsonConversionMap(String jsonStr, Class<K> kClass, Class vClass) {
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                return objectMapper.readValue(jsonStr,
                    objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
            } catch (Exception e) {
                LOGGER.warn("jsonConversionBeanArray Error", e);
            }
        }
        return Collections.emptyMap();
    }

}
