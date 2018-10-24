package com.df4j.base.form;

import static com.df4j.base.form.BoundType.*;
import static com.df4j.base.form.RangeType.*;

/**
 * 表单字段类型
 *
 * @param <T>
 */
public class Field<T> {
    private RangeType rangeType = EQUAL;
    private BoundType fromBoundType = INCLUDE;
    private BoundType toBoundType = INCLUDE;
    private T fromValue;
    private T toValue;

    public RangeType getRangeType() {
        return rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }

    public BoundType getFromBoundType() {
        return fromBoundType;
    }

    public void setFromBoundType(BoundType fromBoundType) {
        this.fromBoundType = fromBoundType;
    }

    public BoundType getToBoundType() {
        return toBoundType;
    }

    public void setToBoundType(BoundType toBoundType) {
        this.toBoundType = toBoundType;
    }

    public T getFromValue() {
        return fromValue;
    }

    public void setFromValue(T fromValue) {
        this.fromValue = fromValue;
    }

    public T getToValue() {
        return toValue;
    }

    public void setToValue(T toValue) {
        this.toValue = toValue;
    }

    @Override
    public String toString() {
        String value = "field:[";
        switch (rangeType) {
            case EQUAL_NULL:
                value += "value = null";
                break;
            case EQUAL:
                value += "value = " + String.valueOf(this.getFromValue());
                break;
            case GREATER:
                value += "value" + (this.fromBoundType == INCLUDE ? " >= " : ">")
                        + String.valueOf(this.fromBoundType);
                break;
            case LESS:
                value += "value" + (this.fromBoundType == INCLUDE ? " <= " : " < ")
                        + String.valueOf(this.fromBoundType);
                break;
            case BETWEEN:
                value += String.valueOf(fromValue) + (this.fromBoundType == INCLUDE ? " <= " : " < ") + "value"
                        + (this.toBoundType == INCLUDE ? " <= " : " < ") + String.valueOf(toValue);
                break;
            case NOT_BETWEEN:
                value += "value" + (this.fromBoundType == INCLUDE ? " <= " : " < ") + String.valueOf(this.fromValue)
                        + " || " + "value" + (this.toBoundType == INCLUDE ? " >= " : " > ") + String.valueOf(this.toValue);
                break;
            default:
                break;
        }
        value += "]";
        return value;
    }
}

