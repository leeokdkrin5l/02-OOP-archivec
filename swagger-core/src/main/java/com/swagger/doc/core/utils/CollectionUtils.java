package com.swagger.doc.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    public static <T> List<T> asList(T obj) {
        List<T> result = new ArrayList<>();
        result.add(obj);
        return result;
    }
}
