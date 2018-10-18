package com.df4j.base.range;

/**
 * rangetype 枚举类
 */
public class RangeType {
    /**
     * 等值
     */
    public static int EQUAL = 0;

    /**
     * 大于值的范围
     */
    public static int GREATER = 1;

    /**
     * 小于值
     */
    public static int LESS = 2;

    /**
     * 中间
     */
    public static int BETWEEN = 3;

    /**
     * 两边
     */
    public static int NOT_BETWEEN = 4;

    /**
     * 所有
     */
    public static int ALL = 5;

    /**
     * 无
     */
    public static int NULL = 6;

    /**
     * 等于空
     */
    public static int EQUAL_NULL = 7;
}