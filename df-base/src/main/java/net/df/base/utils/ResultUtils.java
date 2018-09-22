package net.df.base.utils;

import net.df.base.constants.ErrorCode;
import net.df.base.server.MultiResult;
import net.df.base.server.Result;
import net.df.base.server.SingleResult;
import java.util.List;

/**
 * 构造返回结果的工具类
 */
public class ResultUtils {

    /**
     * 构建结果对象
     * @param errorNo
     * @param errorInfo
     * @param pageNo
     * @param pageSize
     * @param result
     * @param total
     * @return
     */
    public static Result build(Integer errorNo, String errorInfo, Integer pageNo, Integer pageSize, Integer total, Object result){
        Result returnResult = null;
        if(result instanceof List){
            returnResult = new MultiResult();
            if(ValidateUtils.notNull(pageNo)){
                ((MultiResult)returnResult).setPageNo(pageNo);
                ((MultiResult)returnResult).setPageSize(pageSize);
                ((MultiResult)returnResult).setTotal(total);
            }
        }else{
            returnResult = new SingleResult();
        }
        returnResult.setResult(result);
        returnResult.setErrorNo(errorNo);
        returnResult.setErrorInfo(errorInfo);
        return returnResult;
    }


    /**
     * 构建结果对象
     * @param errorNo
     * @param errorInfo
     * @param pageNo
     * @param pageSize
     * @param result
     * @return
     */
    public static Result build(Integer errorNo, String errorInfo, Integer pageNo, Integer pageSize, Object result){
        return build(errorNo,errorInfo, pageNo, pageSize, null, result);
    }

    /**
     * 构建结果对象
     * @param errorNo
     * @param errorInfo
     * @param object
     * @return
     */
    public static Result build(Integer errorNo, String errorInfo, Object object){
        return build(errorNo, errorInfo, null, null, object);
    }


    /**
     * 成功返回
     * @param pageNo
     * @param pageSize
     * @param total
     * @param resultObject
     * @return
     */
    public static Result success(Integer pageNo, Integer pageSize, Integer total, Object resultObject){
        return build(ErrorCode.SUCCESS, "调用成功", pageNo, pageSize, total, resultObject);
    }

    /**
     * 成功返回
     * @param pageNo
     * @param pageSize
     * @param resultObject
     * @return
     */
    public static Result success(Integer pageNo, Integer pageSize, Object resultObject){
        return build(ErrorCode.SUCCESS, "调用成功", pageNo, pageSize, resultObject);
    }

    /**
     * 成功返回
     * @param resultObject
     * @return
     */
    public static Result success(Object resultObject){
        return success(null, null, resultObject);
    }


    /**
     * 错误返回
     * @param errorNo
     * @param errorInfo
     * @return
     */
    public static Result error(int errorNo, String errorInfo){
        return error(errorNo, errorInfo, null);
    }

    /**
     * 错误返回
     * @param errorNo
     * @param errorInfo
     * @param resultObject
     * @return
     */
    public static Result error(int errorNo, String errorInfo, Object resultObject){
        return build(errorNo, errorInfo, resultObject);
    }
}
