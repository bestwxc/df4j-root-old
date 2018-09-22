package net.df.base.server;

import net.df.base.constants.ResultType;

/**
 * 单结果集
 * @param <T>
 */
public class SingleResult<T> extends Result<T>{
    public SingleResult(){
        super(ResultType.OBJECT);
    }
}
