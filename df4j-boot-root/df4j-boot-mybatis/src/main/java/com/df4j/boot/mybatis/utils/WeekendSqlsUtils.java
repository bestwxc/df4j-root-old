package com.df4j.boot.mybatis.utils;

import com.df4j.base.exception.DfException;
import com.df4j.base.range.Range;
import com.df4j.base.utils.ValidateUtils;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.WeekendSqls;
import static com.df4j.base.range.RangeType.*;
import static com.df4j.base.range.BoundType.*;

/**
 * 拼接WeekendSqls的工具类
 */
public class WeekendSqlsUtils {
    public static WeekendSqls appendSql(WeekendSqls sqls, Fn fn, Object value){
        // 如果值为空，不拼接
        if(ValidateUtils.isNull(value)){
            return sqls;
        }
        // 不是range子类
        if(!(value instanceof Range)){
            return sqls.andEqualTo(fn, value);
        }
        Range range = (Range) value;
        int rangeType = range.getRangeType();
        int boundType = range.getBoundType();

        // 该条件无筛选作用
        if(rangeType == ALL){
            return sqls;
        }

        // 等值范围
        if(rangeType == EQUAL){
            return sqls.andEqualTo(fn, value);
        }
        // 大于范围
        if(rangeType == GREATER){
            if(boundType == EXCLUDE){
                sqls.andGreaterThan(fn, value);
            }else{
                sqls.andGreaterThanOrEqualTo(fn, value);
            }
        }
        // 小于范围
        if(rangeType == LESS){
            if(boundType == EXCLUDE){
                sqls.andLessThan(fn, value);
            }else{
                sqls.andLessThanOrEqualTo(fn, value);
            }
        }
        // between
        if(rangeType == BETWEEN){
            if(boundType == EXCLUDE){
                sqls.andGreaterThan(fn, value);
                sqls.andLessThan(fn, range.getToRange());
            }else if(boundType == INCLUDE){
                sqls.andGreaterThanOrEqualTo(fn, value);
                sqls.andLessThanOrEqualTo(fn, range.getToRange());
            }else if(boundType == LEFT_INCLUDE){
                sqls.andGreaterThanOrEqualTo(fn, value);
                sqls.andLessThan(fn, range.getToRange());
            }else if(boundType == RIGHT_INCLUDE){
                sqls.andGreaterThan(fn, value);
                sqls.andLessThanOrEqualTo(fn, range.getToRange());
            }
            return sqls;
        }
        // not between
        if(rangeType == NOT_BETWEEN){
            if(boundType == EXCLUDE){
                sqls.andLessThan(fn, value);
                sqls.andGreaterThan(fn, range.getToRange());
            }else if(boundType == INCLUDE){
                sqls.andLessThanOrEqualTo(fn, value);
                sqls.andGreaterThanOrEqualTo(fn, range.getToRange());
            }else if(boundType == LEFT_INCLUDE){
                sqls.andLessThanOrEqualTo(fn, value);
                sqls.andGreaterThan(fn, range.getToRange());
            }else if(boundType == RIGHT_INCLUDE){
                sqls.andLessThan(fn, value);
                sqls.andGreaterThanOrEqualTo(fn, range.getToRange());
            }
            return sqls;
        }
        // 等于null
        if(rangeType == EQUAL_NULL){
            return sqls.andEqualTo(fn, null);
        }
        // 删选条件逻辑错误，不可能有任何记录满足条件
        if(rangeType == NULL){
            throw new DfException("rangeType为NULL，将没有记录满足条件");
        }
        return sqls;
    }
}
