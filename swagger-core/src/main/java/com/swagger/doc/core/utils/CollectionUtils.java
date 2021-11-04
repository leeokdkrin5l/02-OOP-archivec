package com.swagger.doc.core.utils;

/**
 * @author kangwang
 * @Description:
 * @date 2020/8/24
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {
    public static <T> boolean isEmpty(T[] arrays) {
        if (arrays == null || arrays.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}
