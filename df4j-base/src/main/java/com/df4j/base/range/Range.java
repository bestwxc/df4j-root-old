package com.df4j.base.range;

/**
 * 表示范围
 * @param <T>
 */
public interface Range<T> {

    /**
     * 设置rangeType
     * @param rangeType
     */
    void setRangeType(int rangeType);

    /**
     * 获取范围类型
     * @return
     */
    default int getRangeType(){
        return 0;
    }

    /**
     * 获取toRange
     * @return
     */
    T getToRange();

    /**
     * 设置toRange
     * @param toRange
     */
    void setToRange(T toRange);
}
