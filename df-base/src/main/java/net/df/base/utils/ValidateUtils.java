package net.df.base.utils;

import java.util.List;

/**
 * 校验工具类
 */
public class ValidateUtils {

    public static boolean isEmptyString(String value){
        return isNull(value) || value.trim().length() == 0;
    }

    public static boolean isNotEmptyString(String value){
        return !isEmptyString(value);
    }

    public static boolean notEmpty(List<?> list){
        return list != null && list.size() > 0;
    }

    public static boolean isEmpty(List<?> list){
        return !notEmpty(list);
    }

    public static boolean notNull(Object object){
        return !isNull(object);
    }

    public static boolean isNull(Object object){
        return object == null;
    }
}
