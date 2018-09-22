package net.df.base.exception;

import net.df.base.constants.ErrorCode;

/**
 * 业务异常子类，入参不合法
 */
public class ParamIllegalException extends BusinessException{

    /**
     * 构造函数
     */
    public ParamIllegalException(){
        super(ErrorCode.VALIDATE_FAIL);
    }

    /**
     * 构造函数
     * @param message
     */
    public ParamIllegalException(String message){
        super(ErrorCode.VALIDATE_FAIL, message);
    }
}
