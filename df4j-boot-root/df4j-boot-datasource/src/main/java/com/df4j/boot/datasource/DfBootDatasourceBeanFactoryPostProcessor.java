package com.df4j.boot.datasource;

import com.df4j.base.exception.DfException;
import com.df4j.base.utils.StringUtils;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.boot.properties.DfBootDatasourceProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 自定义数据源配置类
 */
@Component
@ConditionalOnProperty(prefix = "df.boot.datasource", name = "enabled", havingValue = "true")
public class DfBootDatasourceBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor {

    @Autowired
    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取配置的对象
        DfBootDatasourceProperties dfBootDatasourceProperties = new DfBootDatasourceProperties();
        Binder.get(environment).bind("df.boot.datasource", Bindable.ofInstance(dfBootDatasourceProperties));

        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        // 注入配置的数据源
        Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> datasources = dfBootDatasourceProperties.getDatasources();
        for(String datasourceKey: datasources.keySet()){
            DfBootDatasourceProperties.DatasourceNodeProperties datasource = datasources.get(datasourceKey);
            Map<String, Map<String, Object>> nodes = datasource.getNodes();
            for(String nodeKey: nodes.keySet()){
                String beanName = datasourceKey + StringUtils.objectNameToClassName(nodeKey) + "DataSource";
                DataSource dataSource = this.initDataSource(datasource.getType(), datasourceKey, nodeKey);
                factory.registerSingleton(beanName, dataSource);
            }
        }
    }

    private DataSource initDataSource(String type, String datasourceKey, String nodeKey){
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        Binder.get(environment).bind("spring.datasource", Bindable.ofInstance(dataSourceProperties));
        Binder.get(environment).bind("df.boot.datasource.datasources." + datasourceKey + ".nodes." + nodeKey, Bindable.ofInstance(dataSourceProperties));
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(this.getDatasourceClass(type)).build();
        Binder.get(environment).bind("spring.datasource." + type, Bindable.ofInstance(dataSource));
        Binder.get(environment).bind("df.boot.datasource.datasources." + datasourceKey + ".nodes." + nodeKey, Bindable.ofInstance(dataSource));
        ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(dataSource.getClass(), "init"), dataSource);
        return dataSource;
    }

    private Class<? extends DataSource> getDatasourceClass(String type){
        String className = null;
        switch (type.toUpperCase()){
            case "hikari": className = "com.zaxxer.hikari.HikariDataSource";break;
            case "dbcp2": className = "org.apache.commons.dbcp2.BasicDataSource";break;
            case "druid": className = "com.alibaba.druid.pool.DruidDataSource";break;
            case "c3p0": className = "com.mchange.v2.c3p0.ComboPooledDataSource";break;
            default: className = "com.zaxxer.hikari.HikariDataSource"; break;
        }
        try{
            return (Class<? extends DataSource>) Class.forName(className);
        }catch (Exception e){
            throw new DfException("未找到类" + className, e);
        }
    }


    /**
     * 校验多数据源配置
     * @param dfBootDatasourceProperties
     */
    private void validProperties(DfBootDatasourceProperties dfBootDatasourceProperties){
        if(ValidateUtils.isEmptyString(dfBootDatasourceProperties.getDefaultKey())){
            throw new DfException("默认数据源不能未空");
        }
        Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> datasources = dfBootDatasourceProperties.getDatasources();
        if(!datasources.containsKey(dfBootDatasourceProperties.getDefaultKey())){
            throw new DfException("未找到默认数据源的详细配置");
        }
        for(String key : datasources.keySet()){
            DfBootDatasourceProperties.DatasourceNodeProperties datasource = datasources.get(key);
            if(ValidateUtils.isEmptyString(datasource.getType())){
                throw new DfException("数据源[" + key + "]未配置数据源类型");
            }
            if(ValidateUtils.isEmptyString(datasource.getMaster())){
                throw new DfException("数据源[" + key + "]未配置master节点key");
            }
            Map<String, Map<String, Object>> nodes = datasource.getNodes();
            if(!nodes.containsKey(datasource.getMaster())){
                throw new DfException("数据源[" + key + "]未找到master节点的配置");
            }
        }
    }
}
