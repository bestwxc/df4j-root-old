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
            return null;
        }
        if(!subject.isAuthenticated()){
            return null;
        }
        Object object = subject.getPrincipal();
        if(ValidateUtils.isNull(object)){
            return null;
        }
        String userName = String.valueOf(object);
        if(ValidateUtils.isEmptyString(userName)){
            return null;
        }
        return userName;
    }
}
