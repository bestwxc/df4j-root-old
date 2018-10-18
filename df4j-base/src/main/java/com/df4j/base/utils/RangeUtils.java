package com.df4j.base.utils;

import com.df4j.base.exception.DfException;
import com.df4j.base.range.Range;
import com.df4j.base.range.RangeProxyFactory;
import static com.df4j.base.range.RangeType.*;

public class RangeUtils {
    /**
     * 创建 range
     * @param clazz
     * @param rangeType
     * @param fromRange
     * @param toRange
     * @param <T>
     * @return
     */
    public static <T> T createRange(Class<T> clazz, int rangeType, T fromRange, T toRange){
        // 判断是否能够使用范围类型，必须实现Comparable接口
        if(rangeType == EQUAL || rangeType == NULL){
            if(!clazz.isAssignableFrom(Comparable.class)){
                throw new DfException(clazz.getName() + "没有实现Comparable接口，无法使用range");
            }
        }
        T proxy = RangeProxyFactory.create(clazz, fromRange);
        Range<T> range = (Range<T>) proxy;
        range.setRangeType(rangeType);
        // a < x < b 如果 a >= b, 则没有记录满足
        if(BETWEEN == rangeType){
            range.setToRange(toRange);
            Comparable<T> comparable = (Comparable<T>) proxy;
            if(comparable.compareTo(toRange) >= 0){
                range.setRangeType(NULL);
            }
        }
        /**
         * x < a || x > b 如果 a > b 则所有范围均满足
         */
        if(rangeType == NOT_BETWEEN){
            range.setToRange(toRange);
            Comparable<T> comparable = (Comparable<T>) proxy;
            if(comparable.compareTo(toRange) > 0){
                range.setRangeType(ALL);
            }
        }
        return proxy;
    }

    /**
     * 创建等值
     * @param clazz
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T createEqual(Class<T> clazz, T object){
        return createRange(clazz, EQUAL, object, null);
    }

    /**
     * 创建大于区间
     * @param clazz
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T createGreater(Class<T> clazz, T object){
        return createRange(clazz, GREATER, object, null);
    }

    /**
     * 创建小于区间
     * @param clazz
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T createLess(Class<T> clazz, T object){
        return createRange(clazz, LESS, object, null);
    }

    /**
     * 创建中间范围
     * @param clazz
     * @param object
     * @param toRange
     * @param <T>
     * @return
     */
    public static <T> T createBetween(Class<T> clazz, T object, T toRange){
        return createRange(clazz, BETWEEN, object, toRange);
    }

    /**
     * 创建notBetween
     * @param clazz
     * @param object
     * @param toRange
     * @param <T>
     * @return
     */
    public static <T> T createNotBetween(Class<T> clazz, T object, T toRange){
        return createRange(clazz, NOT_BETWEEN, object, toRange);
    }

    /**
     * 创建NULL
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createNull(Class<T> clazz){
        return createRange(clazz, NULL, null, null);
    }

    /**
     * 创建所有
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createAll(Class<T> clazz){
        return createRange(clazz, ALL, null, null);
    }
}
