package net.df.boot.web.session;

import net.df.base.constants.ErrorCode;
import net.df.base.exception.BusinessException;
import net.df.base.utils.ValidateUtils;
import net.df.boot.web.constants.SessionKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session工具类
 */
public class SessionManager {

    /**
     * 获取session
     * @param request
     * @param create
     * @return
     */
    public static HttpSession getSession(HttpServletRequest request, boolean create){
        return request.getSession(create);
    }

    /**
     * 获取session
     * @param request
     * @return
     */
    public static HttpSession getSession(HttpServletRequest request){
        return getSession(request, true);
    }

    /**
     * 获取session中的对象
     * @param httpSession
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getSessionObject(HttpSession httpSession, String key, Class<T> tClass){
        return (T) httpSession.getAttribute(key);
    }

    /**
     * 获取session中的对象
     * @param request
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getSessionObject(HttpServletRequest request, String key, Class<T> clazz){
        return getSessionObject(getSession(request),key,clazz);
    }

    /**
     * 将对象放入session中
     * @param request
     * @param key
     * @param object
     */
    public static void setSessionObject(HttpServletRequest request, String key, Object object){
        setSessionObject(getSession(request), key, object);
    }

    /**
     * 将对象放入session中
     * @param httpSession
     * @param key
     * @param object
     */
    public static void setSessionObject(HttpSession httpSession, String key, Object object){
        httpSession.setAttribute(key,object);
    }

    /**
     * 校验是否登录
     * @param request
     * @param sessionUserKey
     */
    public static void validateLogin(HttpServletRequest request, String sessionUserKey){
        validateLogin(getSession(request, false), sessionUserKey);
    }



    /**
     * 校验是否登录
     * @param httpSession
     * @param sessionUserKey
     */
    public static void validateLogin(HttpSession httpSession, String sessionUserKey){
        if(!isLogin(httpSession, sessionUserKey)){
            throw new BusinessException(ErrorCode.UNLOGIN, "unlogin");
        }
    }

    /**
     * 校验是否登录
     * @param httpSession
     */
    public static void validateLogin(HttpSession httpSession){
        validateLogin(httpSession, SessionKey.SESSION_USER_KEY);
    }

    /**
     * 校验是否登录
     * @param request
     */
    public static void validateLogin(HttpServletRequest request){
        validateLogin(request, SessionKey.SESSION_USER_KEY);
    }


    /**
     * 校验是否登录
     * @param request
     * @param sessionUserKey
     */
    public static boolean isLogin(HttpServletRequest request, String sessionUserKey){
        HttpSession httpSession = getSession(request, false);
        return isLogin(httpSession, sessionUserKey);
    }

    /**
     * 校验是否登录
     * @param httpSession
     * @param sessionUserKey
     * @return
     */
    public static boolean isLogin(HttpSession httpSession, String sessionUserKey){
        return ValidateUtils.isNull(httpSession)
                || ValidateUtils.isNull(httpSession.getAttribute(sessionUserKey));
    }

    /**
     * 校验是否登录
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request){
        return isLogin(request, SessionKey.SESSION_USER_KEY);
    }

    /**
     * 校验是否登录
     * @param httpSession
     * @return
     */
    public static boolean isLogin(HttpSession httpSession){
        return isLogin(httpSession, SessionKey.SESSION_USER_KEY);
    }
}
