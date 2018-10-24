package com.df4j.base.utils;

import com.df4j.base.form.BoundType;
import com.df4j.base.form.Field;
import com.df4j.base.form.FieldFactory;
import com.df4j.base.form.RangeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.df4j.base.form.RangeType.*;

public class FieldUtils {
    public static Field getField(RangeType rangeType, Object fieldValue, Object fromValue, BoundType fromBoundType, Object toValue, BoundType toBoundType) {
        if (rangeType == EQUAL_NULL) {
            return FieldFactory.buildNull();
        } else if (rangeType == EQUAL) {
            if (ValidateUtils.isNull(fieldValue)) {
                return null;
            }
            return FieldFactory.buildEqual(fieldValue);
        } else if (rangeType == GREATER || rangeType == LESS) {
            if (ValidateUtils.isNull(fieldValue)) {
                return null;
            }
            return FieldFactory.buildField(rangeType, fieldValue, fromBoundType, null, null);
        } else {
            if (ValidateUtils.isNull(fromValue) && ValidateUtils.isNull(toValue)) {
                return null;
            } else if (ValidateUtils.isNull(toValue)) {
                return FieldFactory.buildField(GREATER, fromValue, fromBoundType, null, null);
            } else if (ValidateUtils.isNull(fromValue)) {
                return FieldFactory.buildField(LESS, toValue, toBoundType, null, null);
            } else {
                return FieldFactory.buildField(rangeType, fromValue, fromBoundType, toValue, toBoundType);
            }
        }
    }

    /**
     * 获取String字段
     *
     * @param map
     * @param fieldName
     * @param range
     * @param fromBoundType
     * @param toBoundType
     * @return
     */
    public static Field<String> getStringField(Map<String, ?> map, String fieldName, boolean range, BoundType fromBoundType, BoundType toBoundType) {
        if (!range) {
            String fieldValue = MapUtils.getStringFromMap(map, fieldName, null);
            return getField(EQUAL, fieldValue, null, null, null, null);
        } else {
            String fromValue = MapUtils.getStringFromMap(map, getFromFieldName(fieldName), null);
            String toValue = MapUtils.getStringFromMap(map, getToFieldName(fieldName), null);
            return getField(BETWEEN, null, fromValue, fromBoundType, toValue, toBoundType);
        }
    }

    /**
     * 获取Integer字段
     *
     * @param map
     * @param fieldName
     * @param range
     * @param fromBoundType
     * @param toBoundType
     * @return
     */
    public static Field<Integer> getIntegerField(Map<String, ?> map, String fieldName, boolean range, BoundType fromBoundType, BoundType toBoundType) {
        if (!range) {
            Integer fieldValue = MapUtils.getIntegerFromMap(map, fieldName, null);
            return getField(EQUAL, fieldValue, null, null, null, null);
        } else {
            Integer fromValue = MapUtils.getIntegerFromMap(map, getFromFieldName(fieldName), null);
            Integer toValue = MapUtils.getIntegerFromMap(map, getToFieldName(fieldName), null);
            return getField(BETWEEN, null, fromValue, fromBoundType, toValue, toBoundType);
        }
    }

    /**
     * 获取Long字段
     *
     * @param map
     * @param fieldName
     * @param range
     * @param fromBoundType
     * @param toBoundType
     * @return
     */
    public static Field<Long> getLongField(Map<String, ?> map, String fieldName, boolean range, BoundType fromBoundType, BoundType toBoundType) {
        if (!range) {
            Long fieldValue = MapUtils.getLongFromMap(map, fieldName, null);
            return getField(EQUAL, fieldValue, null, null, null, null);
        } else {
            Long fromValue = MapUtils.getLongFromMap(map, getFromFieldName(fieldName), null);
            Long toValue = MapUtils.getLongFromMap(map, getToFieldName(fieldName), null);
            return getField(BETWEEN, null, fromValue, fromBoundType, toValue, toBoundType);
        }
    }

    /**
     * 获取BigDecimal字段
     *
     * @param map
     * @param fieldName
     * @param range
     * @param fromBoundType
     * @param toBoundType
     * @return
     */
    public static Field<BigDecimal> getBigDecimalField(Map<String, ?> map, String fieldName, boolean range, BoundType fromBoundType, BoundType toBoundType) {
        if (!range) {
            BigDecimal fieldValue = MapUtils.getBigDecimalFromMap(map, fieldName, null);
            return getField(EQUAL, fieldValue, null, null, null, null);
        } else {
            BigDecimal fromValue = MapUtils.getBigDecimalFromMap(map, getFromFieldName(fieldName), null);
            BigDecimal toValue = MapUtils.getBigDecimalFromMap(map, getToFieldName(fieldName), null);
            return getField(BETWEEN, null, fromValue, fromBoundType, toValue, toBoundType);
        }
    }

    /**
     * 获取Date字段
     *
     * @param map
     * @param fieldName
     * @param range
     * @param fromBoundType
     * @param toBoundType
     * @return
     */
    public static Field<Date> getDateField(Map<String, ?> map, String fieldName, String pattern, boolean range, BoundType fromBoundType, BoundType toBoundType) {
        if (!range) {
            Date fieldValue = MapUtils.getDateFromMap(map, fieldName, pattern, null);
            return getField(EQUAL, fieldValue, null, null, null, null);
        } else {
            Date fromValue = MapUtils.getDateFromMap(map, getFromFieldName(fieldName), pattern, null);
            Date toValue = MapUtils.getDateFromMap(map, getToFieldName(fieldName), pattern, null);
            return getField(BETWEEN, null, fromValue, fromBoundType, toValue, toBoundType);
        }
    }

    /**
     * 获取FromFieldName
     *
     * @param fieldName
     * @return
     */
    public static String getFromFieldName(String fieldName) {
        return "from" + StringUtils.objectNameToClassName(fieldName);
    }

    /**
     * 获取ToFieldName
     *
     * @param fieldName
     * @return
     */
    public static String getToFieldName(String fieldName) {
        return "to" + StringUtils.objectNameToClassName(fieldName);
    }
}
