package com.df4j.base.server;

import com.df4j.base.constants.ResultType;

/**
 * 单结果集
 * @param <T>
 */
public class SingleResult<T> extends Result<T>{
    public SingleResult(){
        super(ResultType.OBJECT);
    }

    public SingleResult(Object result){
        this();
        this.setResult((T)result);
    }
}
