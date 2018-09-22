package net.df.base.exception;


import net.df.base.constants.ErrorCode;

/**
 * 业务异常
 */
public class BusinessException extends DfException{
    private Integer errorNo;

    public BusinessException(){
        this(ErrorCode.UNCATCH_BUSINESS_EXCEPTION);
    }
    public BusinessException(Integer errorNo){
        super();
        this.errorNo = errorNo;
    }

    public BusinessException(Integer errorNo, String message, Throwable cause){
        super(message, cause);
        this.errorNo = errorNo;
    }

    public BusinessException(Integer errorNo, String message){
        super(message);
        this.errorNo = errorNo;
    }

    public BusinessException(Integer errorNo, Throwable cause){
        super(cause);
        this.errorNo = errorNo;
    }

    public Integer getErrorNo() {
        return errorNo;
    }
}
