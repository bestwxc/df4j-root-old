package com.df4j.boot.datasource;


import com.df4j.base.utils.JsonUtils;
import com.df4j.base.utils.RegexUtils;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.boot.properties.DfBootDatasourceProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "df.boot.datasource", name = "enabled", havingValue = "true")
public class DfDataSourceChangeAspect {

    private Logger logger = LoggerFactory.getLogger(DfDataSourceChangeAspect.class);

    private static String REGEX = "(?<=module\\.).+(?=\\.service)";

    @Autowired
    private DfBootDatasourceProperties dfBootDatasourceProperties;

    @Pointcut("execution(public * com..module.*.service.*.*(..))" +
            " || execution(public * cn..module.*.service.*.*(..))" +
            " || execution(public * org..module.*.service.*.*(..))" +
            " || execution(public * net..module.*.service.*.*(..))" +
            " || @annotation(com.df4j.boot.datasource.UseDataSource)")
    public void useDataSource() {}

    @Before("useDataSource()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Method method = ReflectionUtils.findMethod(joinPoint.getTarget().getClass(), methodName);
        String dataSourceKey = null;
        String nodeKey = null;
        UseDataSource useDataSource = method.getAnnotation(UseDataSource.class);
        if(useDataSource != null){
            dataSourceKey = useDataSource.value();
        } else {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String moduleName = RegexUtils.getOneMatch(className, REGEX);
            for(String key: dfBootDatasourceProperties.getDatasources().keySet()){
                DfBootDatasourceProperties.DatasourceNodeProperties tmp = dfBootDatasourceProperties.getDatasources().get(key);
                List<String> supportModules = tmp.getModules();
                if(ValidateUtils.notEmpty(supportModules)){
                    if(supportModules.contains(moduleName)){
                        dataSourceKey = key;
                        break;
                    }
                }
            }
        }
        boolean useSlave = methodName.startsWith("select") || methodName.startsWith("query") || methodName.startsWith("list");
        DataSourceNodeManager.setDataSource(dataSourceKey, !useSlave);
    }

    @After("useDataSource()")
    public void doAfter() {

    }

    @AfterReturning(returning = "object", pointcut = "useDataSource()")
    public void afterReturning(Object object) {

    }
}
