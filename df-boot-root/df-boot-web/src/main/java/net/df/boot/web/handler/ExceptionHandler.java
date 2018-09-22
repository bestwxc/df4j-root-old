package net.df.boot.web.handler;

import net.df.base.constants.ErrorCode;
import net.df.base.exception.BusinessException;
import net.df.base.exception.DfException;
import net.df.base.server.Result;
import net.df.base.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Result handler(Throwable t){
        Integer errorNo = null;
        String errorInfo = null;
        if( t instanceof DuplicateKeyException){
            errorNo = ErrorCode.DUPLICATE_RECORD;
            errorInfo = "与已有记录冲突";
            logger.info("errorNo:{},errorInfo:{}",errorNo,errorInfo,t);
        } else if(t instanceof BusinessException){
            BusinessException be = (BusinessException) t;
            errorNo = be.getErrorNo();
            errorInfo = be.getMessage();
            logger.info("业务异常，errorNo:{},errorInfo:{}",errorNo,errorInfo,t);
        }else if(t instanceof HttpMessageNotReadableException){
            errorNo = ErrorCode.REQUEST_FORMAT_ERROR;
            errorInfo = "请求接口格式不正确";
            logger.warn("errorNo:{},errorInfo:{}",errorNo,errorInfo,t);
        }else if(t instanceof DfException){
            errorNo = ErrorCode.UNCATCH_BUSINESS_EXCEPTION;
            errorInfo = t.getMessage();
            logger.error("errorNo:{},errorInfo:{}",errorNo,errorInfo,t);
        }else{
            errorNo = ErrorCode.SYSTEM_ERROR;
            errorInfo = "系统异常:" + t.getMessage();
            logger.error("errorNo:{},errorInfo:{}",errorNo,errorInfo,t);
        }
        return ResultUtils.error(errorNo, errorInfo);
    }
}
