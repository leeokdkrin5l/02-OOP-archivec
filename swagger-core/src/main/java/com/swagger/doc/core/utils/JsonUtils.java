package com.swagger.doc.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-17 下午6:17
 */
public class JsonUtils {
    public static final ObjectMapper OBJECT_MAPPER     = new ObjectMapper();
    public static final ObjectMapper OBJECT_MAPPER_ALL = new ObjectMapper();
    private static final Logger      LOGGER            = LoggerFactory.getLogger(JsonUtils.class);
    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER_ALL.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public static String toJson(Object object) {
        if (object != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                LOGGER.warn("JsonProcessingException", e);
            }
        }
        return StringUtils.EMPTY;
    }

    public static String toNullJson(Object object) {
        if (object != null) {
            try {
                return OBJECT_MAPPER_ALL.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                LOGGER.warn("JsonProcessingException", e);
            }
        }
        return StringUtils.EMPTY;
    }
}
