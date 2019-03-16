package com.df4j.boot.datasource;

import com.df4j.base.exception.DfException;
import com.df4j.base.utils.JsonUtils;
import com.df4j.base.utils.MapUtils;
import com.df4j.base.utils.StringUtils;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.boot.properties.DfBootDatasourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义数据源配置类
 */
@Component
@ConditionalOnProperty(prefix = "df.boot.datasource", name = "enabled", havingValue = "true")
public class DfBootDatasourceBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor {

    private Logger logger = LoggerFactory.getLogger(DfBootDatasourceBeanFactoryPostProcessor.class);


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        logger.debug("开始配置自定义多数据源");
        // 获取配置的对象
        Environment environment = beanFactory.getBean(Environment.class);
        DfBootDatasourceProperties dfBootDatasourceProperties = new DfBootDatasourceProperties();
        Binder.get(environment).bind("df.boot.datasource", Bindable.ofInstance(dfBootDatasourceProperties));

        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;

        // 注入配置的数据源
        Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> datasources = dfBootDatasourceProperties.getDatasources();
        logger.info("配置数据源有：{}", datasources.keySet());
        DfDynamicDataSource dynamicDataSource = new DfDynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        this.configDataSource(environment, factory, datasources, dynamicDataSource, targetDataSources);
        DataSourceNodeManager.setDefaultDataSourceKey(dfBootDatasourceProperties.getDefaultKey());
        DataSource defaultDataSource = beanFactory.getBean(DataSourceNodeManager.getDefaultDataSourceKey(), DataSource.class);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);

        // 加载自定义配置中的数据源配置
        List<String> classNames = dfBootDatasourceProperties.getCustomDataSourceConfigLoadClass();
        if(ValidateUtils.notEmpty(classNames)){
            logger.info("自定义数据源配置类不为空，开始处理自定义数据源类");
            for(String className: classNames) {
                try {
                    logger.info("开始处理自定义数据源类,class:{}", className);
                    DfDataSourceConfigure dfDataSourceConfigure = (DfDataSourceConfigure) Class.forName(className).newInstance();
                    Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> configDatasources = dfDataSourceConfigure.loadConfig(defaultDataSource);
                    this.configDataSource(environment, factory, datasources, dynamicDataSource, targetDataSources);
                } catch (Throwable t) {
                    logger.error("获取自定义数据源配置失败, className:" + className, t);
                }
            }
        }
        dynamicDataSource.afterPropertiesSet();
        factory.registerSingleton("dataSource", dynamicDataSource);
        logger.debug("配置自定义多数据源完成");
    }

    /**
     * 配置数据源
     * @param environment SpringBoot环境配置
     * @param factory Spring BeanFactory
     * @param datasources 数据源配置
     * @param dynamicDataSource 动态数据源
     * @param targetDataSources 目标数据源集合
     */
    private void configDataSource(Environment environment, DefaultListableBeanFactory factory, Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> datasources,
                                  DfDynamicDataSource dynamicDataSource, Map<Object, Object> targetDataSources){
        for(String datasourceKey: datasources.keySet()){
            DfBootDatasourceProperties.DatasourceNodeProperties datasource = datasources.get(datasourceKey);
            Map<String, Map<String, Object>> nodes = datasource.getNodes();
            Map<String, String> nodeBeanKeys = new HashMap<>();
            for(String nodeKey: nodes.keySet()){
                logger.debug("开始配置{}数据源{}节点", datasourceKey, nodeKey);
                String beanName = datasourceKey + StringUtils.objectNameToClassName(nodeKey) + "DataSource";
                DataSource dataSource = this.initDataSource(environment, datasource.getType(), datasourceKey, nodeKey);
                nodeBeanKeys.put(nodeKey, beanName);
                factory.registerSingleton(beanName, dataSource);
                logger.info("配置{}数据源{}节点完成", datasourceKey, nodeKey);
                targetDataSources.put(beanName, dataSource);
            }
            DataSourceNodeManager.addDataSource(datasourceKey, datasource.getMaster(), nodeBeanKeys);
        }
    }

    private DataSource initDataSource(Environment environment, String type, String datasourceKey, String nodeKey){
        // 用属性生成对象
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        Binder.get(environment).bind("spring.datasource", Bindable.ofInstance(dataSourceProperties));
        Binder.get(environment).bind("df.boot.datasource.datasources." + datasourceKey + ".nodes." + nodeKey, Bindable.ofInstance(dataSourceProperties));
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(this.getDatasourceClass(type)).build();
        // 再次绑定属性
        Binder.get(environment).bind("spring.datasource." + type, Bindable.ofInstance(dataSource));
        Binder.get(environment).bind("df.boot.datasource.datasources." + datasourceKey + ".nodes." + nodeKey, Bindable.ofInstance(dataSource));
        // 如果需要初始化，则初始化数据源
        if(ValidateUtils.isNotEmptyString(DataSourceType.getDataSourceType(type).getInitMethodName())){
            ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(dataSource.getClass(), DataSourceType.getDataSourceType(type).getInitMethodName()), dataSource);
        }
        return dataSource;
    }

    private Class<? extends DataSource> getDatasourceClass(String name){
        DataSourceType dataSourceType = DataSourceType.getDataSourceType(name);
        String className = dataSourceType.getFullName();
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
            throw new DfException("默认数据源不能为空");
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
