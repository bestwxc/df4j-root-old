package com.df4j.boot.web.utils;

import com.df4j.base.constants.ErrorCode;
import com.df4j.base.exception.BusinessException;
import com.df4j.base.exception.DfException;
import com.df4j.base.utils.ValidateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class CurrentUserUtils {
    public static String getUserName(){
        Subject subject = SecurityUtils.getSubject();
        if(ValidateUtils.isNull(subject)){
            throw new BusinessException(ErrorCode.UNLOGIN, "未登录");
        }
        if(!subject.isAuthenticated()){
            throw new BusinessException(ErrorCode.UNLOGIN, "未登录");
        }
        Object object = subject.getPrincipal();
        if(ValidateUtils.isNull(object)){
            throw new DfException("当前登陆用户名为空");
        }
        String userName = String.valueOf(object);
        if(ValidateUtils.isEmptyString(userName)){
            throw new DfException("当前登陆用户名为空");
        }
        return userName;
    }
}
