package com.df4j.base.form;

import com.df4j.base.utils.ValidateUtils;

import static com.df4j.base.form.BoundType.*;
import static com.df4j.base.form.RangeType.*;

/**
 * Field工具类
 */
public class FieldFactory {

    /**
     * 构建Field
     *
     * @param rangeType
     * @param fromBoundType
     * @param toBoundType
     * @param fromValue
     * @param toValue
     * @return
     */
    public static Field buildField(RangeType rangeType, Object fromValue, BoundType fromBoundType, Object toValue, BoundType toBoundType) {
        if (ValidateUtils.isNull(rangeType)) {
            rangeType = EQUAL;
        }
        if (ValidateUtils.isNull(fromBoundType)) {
            fromBoundType = INCLUDE;
        }
        if (ValidateUtils.isNull(toBoundType)) {
            toBoundType = INCLUDE;
        }
        Field field = new Field();
        field.setRangeType(rangeType);
        // 这三种类型不需要传值
        if (rangeType == EQUAL_NULL) {
            field.setFromBoundType(EXCLUDE);
            return field;
        }
        field.setFromValue(fromValue);
        // 这种情况只需要传一个值
        if (rangeType == EQUAL) {
            field.setFromBoundType(INCLUDE);
            return field;
        }
        // 设置边界类型
        field.setFromBoundType(fromBoundType);
        if (rangeType == BETWEEN || rangeType == NOT_BETWEEN) {
            field.setToValue(toValue);
            field.setToBoundType(toBoundType);
        }
        return field;
    }

    /**
     * 相等的值
     *
     * @param value
     * @return
     */
    public static Field buildEqual(Object value) {
        return buildField(EQUAL, value, null, null, null);
    }

    /**
     * 构建等于空的字段
     *
     * @return
     */
    public static Field buildNull() {
        return buildField(EQUAL_NULL, null, null, null, null);
    }

    /**
     * 大于类型
     *
     * @param value
     * @param boundType
     * @return
     */
    public static Field buildGreater(Object value, BoundType boundType) {
        return buildField(GREATER, value, boundType, null, null);
    }

    /**
     * 小于类型
     *
     * @param value
     * @param boundType
     * @return
     */
    public static Field buildLess(Object value, BoundType boundType) {
        return buildField(LESS, value, boundType, null, null);
    }

    /**
     * between类型
     *
     * @param fromValue
     * @param fromBoundType
     * @param toValue
     * @param toBoundType
     * @return
     */
    public static Field buidBetween(Object fromValue, BoundType fromBoundType, Object toValue, BoundType toBoundType) {
        return buildField(BETWEEN, fromValue, fromBoundType, toValue, toBoundType);
    }

    /**
     * between类型
     *
     * @param fromValue
     * @param toValue
     * @return
     */
    public static Field buidBetween(Object fromValue, Object toValue) {
        return buidBetween(fromValue, INCLUDE, toValue, INCLUDE);
    }

    /**
     * notbetween类型
     *
     * @param fromValue
     * @param fromBoundType
     * @param toValue
     * @param toBoundType
     * @return
     */
    public static Field buidNotBetween(Object fromValue, BoundType fromBoundType, Object toValue, BoundType toBoundType) {
        return buildField(NOT_BETWEEN, fromValue, fromBoundType, toValue, toBoundType);
    }

    /**
     * notbetween类型
     *
     * @param fromValue
     * @param toValue
     * @return
     */
    public static Field buidNotBetween(Object fromValue, Object toValue) {
        return buidNotBetween(fromValue, INCLUDE, toValue, INCLUDE);
    }
}
