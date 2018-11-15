package com.df4j.boot.web.aspect;

import com.df4j.base.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Order(1)
@ConditionalOnProperty(prefix = "df.boot.web", name = "open-http-debug-log",havingValue = "true")
public class HttpDebugLogAspect {
    private Logger logger = LoggerFactory.getLogger(HttpDebugLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.df4j.boot.web.aspect.PrintLog)" +
            " || @within(com.df4j.boot.web.aspect.PrintLog)" +
            " || execution(public * com..controller.*.*(..))" +
            " || execution(public * cn..controller.*.*(..))" +
            " || execution(public * org..controller.*.*(..))" +
            " || execution(public * net..controller.*.*(..))")
    public void httpLog() {

    }

    @Before("httpLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object requestMap = null;
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            for(int i = 0; i < args.length; i++){
                if(args[i] instanceof Map){
                    requestMap = args[i];
                    break;
                }
            }
        }
        logger.debug("request info：url:{},method:{},ip:{},function:{},args:{}",
                request.getRequestURL(),
                request.getMethod(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                JsonUtils.writeObjectAsString(requestMap));

    }

    @After("httpLog()")
    public void doAfter() {
        logger.debug("current request cost:{} ms", System.currentTimeMillis() - startTime.get());
    }

    @AfterReturning(returning = "object", pointcut = "httpLog()")
    public void afterReturning(Object object) {
        logger.debug("response info:{}", JsonUtils.writeObjectAsString(object));
    }
}