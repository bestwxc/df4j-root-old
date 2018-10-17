package com.df4j.base.constants;

/**
 * 错误号常量类
 */
public class ErrorCode {

    /**
     * 成功
     */
    public static Integer SUCCESS = 0;


    /**
     * 校验错误
     */
    public static Integer VALIDATE_FAIL = -1;

    /**
     * 请求格式错误
     */
    public static Integer REQUEST_FORMAT_ERROR = -98;

    /**
     * 系统异常
     */
    public static Integer SYSTEM_ERROR = -99;

    /**
     * 密码或登陆令牌不正确
     */
    public static Integer INCORRECT_CREDENTIALS = -980;

    /**
     * 未授权
     */
    public static Integer UNAUTHORIZED = -998;

    /**
     * 未登录
     */
    public static Integer UNLOGIN = -999;

    /**
     * 重复记录
     */
    public static Integer DUPLICATE_RECORD = -1000;

    /**
     * 未分类业务异常
     */
    public static Integer UNCATCH_BUSINESS_EXCEPTION = -10000;
}
