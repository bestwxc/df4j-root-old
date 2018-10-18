package com.df4j.base.utils;

import com.df4j.base.exception.ParamIllegalException;
import com.df4j.base.range.BoundType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 */
public class MapUtils {

    /**
     * 从map中获取对象
     * @param map
     * @param fieldName
     * @return
     */
    public static Object getObjectFromMapNotNull(Map<String,?> map,String fieldName){
        Object object = map.get(fieldName);
        if(ValidateUtils.isNull(object)){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]为null");
        }
        return object;
    }
    /**
     * 从Map中获取String
     * @param map
     * @param fieldName
     * @param autoTrim
     * @return
     */
    public static String getStringFromMapNotNull(Map<String,?> map,String fieldName,boolean autoTrim){
        Object object = getObjectFromMapNotNull(map,fieldName);
        String value = String.valueOf(object);
        if(ValidateUtils.isEmptyString(value)){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]为空");
        }
        return autoTrim ? value.trim() : value;
    }

    /**
     * 从Map中获取String
     * @param map
     * @param fieldName
     * @param defaultValue
     * @param autoTrim
     * @return
     */
    public static String getStringFromMap(Map<String,?> map,String fieldName,String defaultValue,boolean autoTrim){
        try {
            return getStringFromMapNotNull(map, fieldName,autoTrim);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }



    /**
     * 从Map获取String
     * @param map
     * @param fieldName
     * @return
     */
    public static String getStringFromMapNotNull(Map<String,?> map,String fieldName){
        return getStringFromMapNotNull(map, fieldName, true);
    }

    /**
     * 从Map中获取String
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static String getStringFromMap(Map<String,?> map,String fieldName,String defaultValue){
        return getStringFromMap(map, fieldName, defaultValue,true);
    }

    /**
     * 获取String范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param boundType
     * @return
     */
    public static String getStringRange(Map<String,?> map, String fromFieldName, String toFieldName, int boundType){
        String from = getStringFromMap(map, fromFieldName, null);
        String to = getStringFromMap(map, toFieldName, null);
        return RangeUtils.createFromToRange(String.class, from, to, boundType);
    }

    /**
     * 获取String范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @return
     */
    public static String getStringRange(Map<String,?> map, String fromFieldName, String toFieldName){
        return getStringRange(map, fromFieldName, toFieldName, BoundType.EXCLUDE);
    }

    /**
     * 获取String范围
     * @param map
     * @param fieldName
     * @param boundType
     * @return
     */
    public static String getStringRange(Map<String,?> map, String fieldName, int boundType){
        return getStringRange(map, getFromFieldName(fieldName), getToFieldName(fieldName), boundType);
    }

    /**
     * 获取String范围
     * @param map
     * @param fieldName
     * @return
     */
    public static String getStringRange(Map<String,?> map, String fieldName){
        return getStringRange(map, fieldName, BoundType.EXCLUDE);
    }

    /**
     * 从Map中获取整数
     * @param map
     * @param fieldName
     * @return
     */
    public static Integer getIntegerFromMapNotNull(Map<String,?> map,String fieldName){
        Object object = getObjectFromMapNotNull(map,fieldName);
        try {
            return Integer.valueOf(String.valueOf(object));
        }catch (RuntimeException e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为Integer");
        }
    }

    /**
     * 从Map中获取整数
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static Integer getIntegerFromMap(Map<String,?> map,String fieldName,Integer defaultValue){
        try {
            return getIntegerFromMapNotNull(map, fieldName);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 获取Integer范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param boundType
     * @return
     */
    public static Integer getIntegerRange(Map<String,?> map, String fromFieldName, String toFieldName, int boundType){
        Integer from = getIntegerFromMap(map, fromFieldName, null);
        Integer to = getIntegerFromMap(map, toFieldName, null);
        return RangeUtils.createFromToRange(Integer.class, from, to, boundType);
    }

    /**
     * 获取Integer范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @return
     */
    public static Integer getIntegerRange(Map<String,?> map, String fromFieldName, String toFieldName){
        return getIntegerRange(map, fromFieldName, toFieldName, BoundType.EXCLUDE);
    }

    /**
     * 获取Integer范围
     * @param map
     * @param fieldName
     * @param boundType
     * @return
     */
    public static Integer getIntegerRange(Map<String,?> map, String fieldName, int boundType){
        return getIntegerRange(map, getFromFieldName(fieldName), getToFieldName(fieldName), boundType);
    }

    /**
     * 获取Integer范围
     * @param map
     * @param fieldName
     * @return
     */
    public static Integer getIntegerRange(Map<String,?> map, String fieldName){
        return getIntegerRange(map, fieldName, BoundType.EXCLUDE);
    }

    /**
     * 从Map中获取长整数
     * @param map
     * @param fieldName
     * @return
     */
    public static Long getLongFromMapNotNull(Map<String,?> map,String fieldName){
        Object object = getObjectFromMapNotNull(map,fieldName);
        try {
            return Long.valueOf(String.valueOf(object));
        }catch (RuntimeException e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为Long");
        }
    }

    /**
     * 从Map中获取长整数
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static Long getLongFromMap(Map<String,?> map,String fieldName,Long defaultValue){
        try {
            return getLongFromMapNotNull(map, fieldName);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 获取Long范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param boundType
     * @return
     */
    public static Long getLongRange(Map<String,?> map, String fromFieldName, String toFieldName, int boundType){
        Long from = getLongFromMap(map, fromFieldName, null);
        Long to = getLongFromMap(map, toFieldName, null);
        return RangeUtils.createFromToRange(Long.class, from, to, boundType);
    }

    /**
     * 获取Long范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @return
     */
    public static Long getLongRange(Map<String,?> map, String fromFieldName, String toFieldName){
        return getLongRange(map, fromFieldName, toFieldName, BoundType.EXCLUDE);
    }

    /**
     * 获取Long范围
     * @param map
     * @param fieldName
     * @param boundType
     * @return
     */
    public static Long getLongRange(Map<String,?> map, String fieldName, int boundType){
        return getLongRange(map, getFromFieldName(fieldName), getToFieldName(fieldName),boundType);
    }

    /**
     * 获取Long范围
     * @param map
     * @param fieldName
     * @return
     */
    public static Long getLongRange(Map<String,?> map, String fieldName){
        return getLongRange(map, getFromFieldName(fieldName), getToFieldName(fieldName), BoundType.EXCLUDE);
    }

    /**
     * 从Map中获取浮点数
     * @param map
     * @param fieldName
     * @return
     */
    public static BigDecimal getBigDecimalFromMapNotNull(Map<String,?> map,String fieldName){
        String object = getStringFromMapNotNull(map,fieldName);
        try {
            return new BigDecimal(object);
        }catch (RuntimeException e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为BigDecimal");
        }
    }

    /**
     * 从Map中获取浮点数
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static BigDecimal getBigDecimalFromMap(Map<String,?> map,String fieldName,BigDecimal defaultValue){
        try {
            return getBigDecimalFromMapNotNull(map, fieldName);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 获取BigDecimal范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param boundType
     * @return
     */
    public static BigDecimal getBigDecimalRange(Map<String,?> map, String fromFieldName, String toFieldName, int boundType){
        BigDecimal from = getBigDecimalFromMap(map, fromFieldName, null);
        BigDecimal to = getBigDecimalFromMap(map, toFieldName, null);
        return RangeUtils.createFromToRange(BigDecimal.class, from, to, boundType);
    }

    /**
     * 获取BigDecimal范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @return
     */
    public static BigDecimal getBigDecimalRange(Map<String,?> map, String fromFieldName, String toFieldName){
        return getBigDecimalRange(map, fromFieldName, toFieldName, BoundType.EXCLUDE);
    }

    /**
     * 获取BigDecimal范围
     * @param map
     * @param fieldName
     * @param boundType
     * @return
     */
    public static BigDecimal getBigDecimalRange(Map<String,?> map, String fieldName, int boundType){
        return getBigDecimalRange(map, getFromFieldName(fieldName), getToFieldName(fieldName), boundType);
    }

    /**
     * 获取BigDecimal范围
     * @param map
     * @param fieldName
     * @return
     */
    public static BigDecimal getBigDecimalRange(Map<String,?> map, String fieldName){
        return getBigDecimalRange(map, fieldName, BoundType.EXCLUDE);
    }

    /**
     * 从Map中获取日期
     * @param map
     * @param fieldName
     * @param pattern
     * @return
     */
    public static Date getDateFromMapNotNull(Map<String,?> map,String fieldName,String pattern){
        String object = getStringFromMapNotNull(map,fieldName);
        try{
            return DateUtils.parse(object,pattern);
        }catch (Exception e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为Date");
        }
    }

    /**
     * 从Map中获取日期
     * @param map
     * @param fieldName
     * @param pattern
     * @param defaultValue
     * @return
     */
    public static Date getDateFromMap(Map<String,?> map,String fieldName,String pattern,Date defaultValue){
        try {
            return getDateFromMapNotNull(map, fieldName,pattern);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 获取Date范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param pattern
     * @param boundType
     * @return
     */
    public static Date getDateRange(Map<String,?> map, String fromFieldName, String toFieldName, String pattern, int boundType){
        Date from = getDateFromMap(map, fromFieldName, pattern, null);
        Date to = getDateFromMap(map, toFieldName, pattern, null);
        return RangeUtils.createFromToRange(Date.class, from, to, boundType);
    }

    /**
     * 获取Date范围
     * @param map
     * @param fromFieldName
     * @param toFieldName
     * @param pattern
     * @return
     */
    public static Date getDateRange(Map<String,?> map, String fromFieldName, String toFieldName, String pattern){
        return getDateRange(map, fromFieldName, toFieldName, pattern, BoundType.EXCLUDE);
    }

    /**
     * 获取Date范围
     * @param map
     * @param fieldName
     * @param pattern
     * @param boundType
     * @return
     */
    public static Date getDateRange(Map<String,?> map, String fieldName, String pattern, int boundType){
        return getDateRange(map, getFromFieldName(fieldName), getToFieldName(fieldName), pattern, boundType);
    }

    /**
     * 获取Date范围
     * @param map
     * @param fieldName
     * @param pattern
     * @return
     */
    public static Date getDateRange(Map<String,?> map, String fieldName, String pattern){
        return getDateRange(map, fieldName, pattern, BoundType.EXCLUDE);
    }

    /**
     * 从Map中获取列表
     * @param map
     * @param fieldName
     * @return
     */
    public static List getListFromMapNotNull(Map<String,?> map, String fieldName){
        try{
            return (List) map.get(fieldName);
        }catch (Exception e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为List");
        }
    }

    /**
     * 从Map中获取列表
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static List getListFromMap(Map<String,?> map, String fieldName,List defaultValue){
        try {
            return getListFromMapNotNull(map, fieldName);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 从Map中获取Map
     * @param map
     * @param fieldName
     * @return
     */
    public static Map getMapFromMapNotNull(Map<String,?> map, String fieldName){
        try{
            return (Map) map.get(fieldName);
        }catch (Exception e){
            throw new ParamIllegalException("从map中获取[" + fieldName + "]错误，属性不能转换为Map");
        }
    }

    /**
     * 从Map中获取Map
     * @param map
     * @param fieldName
     * @param defaultValue
     * @return
     */
    public static Map getMapFromMap(Map<String,?> map, String fieldName,Map defaultValue){
        try {
            return getMapFromMapNotNull(map, fieldName);
        }catch (ParamIllegalException e){
            return defaultValue;
        }
    }

    /**
     * 获取FromFieldName
     * @param fieldName
     * @return
     */
    public static String getFromFieldName(String fieldName){
        return "from" + StringUtils.objectNameToClassName(fieldName);
    }

    /**
     * 获取ToFieldName
     * @param fieldName
     * @return
     */
    public static String getToFieldName(String fieldName){
        return "to" + StringUtils.objectNameToClassName(fieldName);
    }
}
