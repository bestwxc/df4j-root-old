package com.df4j.base.exception;


import com.df4j.base.constants.ErrorCode;
import com.df4j.base.utils.ValidateUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务异常
 */
public class BusinessException extends DfException{

    /**
     * 用于保存自定义的异常类型，初始化后不允许改动
     */
    private static Map<Integer,String> errorInfos = new HashMap<>();

    /**
     * 错误号
     */
    private Integer errorNo;

    /**
     * 构造函数
     */
    public BusinessException(){
        this(ErrorCode.UNCATCH_BUSINESS_EXCEPTION);
    }

    /**
     * 构造函数
     * @param errorNo
     */
    public BusinessException(Integer errorNo){
        super();
        this.errorNo = errorNo;
    }

    /**
     * 构造函数
     * @param errorNo
     * @param message
     * @param cause
     */
    public BusinessException(Integer errorNo, String message, Throwable cause){
        super(message, cause);
        this.errorNo = errorNo;
    }

    /**
     * 构造函数
     * @param errorNo
     * @param message
     */
    public BusinessException(Integer errorNo, String message){
        super(message);
        this.errorNo = errorNo;
    }

    /**
     * 构造函数
     * @param errorNo
     * @param cause
     */
    public BusinessException(Integer errorNo, Throwable cause){
        super(cause);
        this.errorNo = errorNo;
    }

    /**
     * 获取错误号
     * @return
     */
    public Integer getErrorNo() {
        return errorNo;
    }

    /**
     * 放入自定义的异常信息
     * @param errorNo
     * @param message
     */
    public synchronized void addConfigMessage(Integer errorNo, String message){
        errorInfos.put(errorNo, message);
    }

    /**
     * build BusinessException
     * @param errorNo
     * @param message
     * @return
     */
    public static BusinessException build(Integer errorNo, String message){
        return build(errorNo, message, null);
    }

    /**
     * build BusinessException
     * @param errorNo
     * @param throwable
     * @return
     */
    public static BusinessException build(Integer errorNo, Throwable throwable){
        return build(errorNo, null, throwable);
    }

    /**
     * build BusinessException
     * @param errorNo
     * @param message
     * @param throwable
     * @return
     */
    public static BusinessException build(Integer errorNo, String message, Throwable throwable){
        String temp = errorInfos.get(errorNo);
        if(ValidateUtils.isEmptyString(temp)){
            temp = message;
        }
        return new BusinessException(errorNo, message, throwable);
    }
}
