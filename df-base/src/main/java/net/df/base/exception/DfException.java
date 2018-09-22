package net.df.base.exception;

/**
 * 自定义异常公共父类
 */
public class DfException extends RuntimeException{
    public DfException() {
        super();
    }

    public DfException(String message) {
        super(message);
    }

    public DfException(String message, Throwable cause) {
        super(message, cause);
    }

    public DfException(Throwable cause) {
        super(cause);
    }

    protected DfException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
