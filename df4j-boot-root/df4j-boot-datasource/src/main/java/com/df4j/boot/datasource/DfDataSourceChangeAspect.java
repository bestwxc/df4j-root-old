package com.df4j.boot.datasource;

import com.df4j.base.utils.RegexUtils;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.boot.properties.DfBootDatasourceProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
@Order(-1000)
@ConditionalOnProperty(prefix = "df.boot.datasource", name = "enabled", havingValue = "true")
public class DfDataSourceChangeAspect {

    private Logger logger = LoggerFactory.getLogger(DfDataSourceChangeAspect.class);

    private static String REGEX = "(?<=module\\.).+(?=\\.service)";

    @Autowired
    private DfBootDatasourceProperties dfBootDatasourceProperties;

    @Pointcut("@annotation(com.df4j.boot.datasource.UseDataSource)" +
            " || @within(com.df4j.boot.datasource.UseDataSource)" +
            " || execution(public * com..service.*.*(..))" +
            " || execution(public * cn..service.*.*(..))" +
            " || execution(public * org..service.*.*(..))" +
            " || execution(public * net..service.*.*(..))")
    public void useDataSource() {}

    @Before("useDataSource()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String dataSourceKey = null;
        String nodeKey = null;
        UseDataSource useDataSource = method.getAnnotation(UseDataSource.class);
        if(ValidateUtils.isNull(useDataSource)){
            useDataSource = joinPoint.getTarget().getClass().getAnnotation(UseDataSource.class);
        }
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
        boolean useSlave = dfBootDatasourceProperties.getDatasources().get(dataSourceKey).isEnableReadNodes();
        if(useSlave){
            useSlave = methodName.startsWith("select") || methodName.startsWith("query") || methodName.startsWith("list");
        }
        DataSourceNodeManager.setDataSource(dataSourceKey, !useSlave);
    }

    @After("useDataSource()")
    public void doAfter() {
        DataSourceNodeManager.cleanDataSource();
    }

    @AfterReturning(returning = "object", pointcut = "useDataSource()")
    public void afterReturning(Object object) {

    }
}
