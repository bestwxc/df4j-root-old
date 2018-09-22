package net.df.base.server;

import net.df.base.constants.ResultType;

/**
 * 返回结果对象
 * @param <T>
 */
public class Result<T>{

    private Integer errorNo;

    private String errorInfo;

    private String resultType;

    private T result;

    public Result(){
        this(ResultType.OBJECT);
    }

    public Result(String resultType){
        this.resultType = resultType;
    }

    public void setErrorNo(Integer errorNo) {
        this.errorNo = errorNo;
    }

    public Integer getErrorNo() {
        return errorNo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultType() {
        return resultType;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
