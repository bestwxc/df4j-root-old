package com.df4j.boot.mybatis.utils;

import com.df4j.base.range.Range;
import com.df4j.base.utils.ValidateUtils;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.WeekendSqls;

public class WeekendSqlsUtils {
    public static WeekendSqls appendSql(WeekendSqls sqls, Fn fn, Object value){

        // 如果值为空，不拼接
        if(ValidateUtils.isNull(value)){
            return sqls;
        }
        if(!(value instanceof Range)){
            return sqls.andEqualTo(fn, value);
        }
        return null;
    }
}
