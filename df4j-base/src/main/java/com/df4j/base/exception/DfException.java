package com.df4j.base.exception;

/**
 * 自定义异常公共父类
 */
public class DfException extends RuntimeException{
    /**
     * 构造函数
     */
    public DfException() {
        super();
    }

    /**
     * 构造函数
     * @param message
     */
    public DfException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param message
     * @param cause
     */
    public DfException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     * @param cause
     */
    public DfException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected DfException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
