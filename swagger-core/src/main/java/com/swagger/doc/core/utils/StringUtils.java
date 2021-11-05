package com.swagger.doc.core.utils;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/24
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String getFileSuffix(String fileName) {
        return substring(fileName, lastIndexOf(fileName, "."), length(fileName));
    }
}
