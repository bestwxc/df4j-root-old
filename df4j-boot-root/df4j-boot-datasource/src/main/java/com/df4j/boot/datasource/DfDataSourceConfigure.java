package com.df4j.boot.datasource;

import com.df4j.boot.properties.DfBootDatasourceProperties;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 自定义数据源配置的接口
 */
public interface DfDataSourceConfigure {
    /**
     * 加载数据源配置
     * 返回的配置中必须包含name, type, driver-class,url, username, password, 其余属性取该类型数据源的值
     * @param dataSource 默认的数据源
     * @return 数据源配置list，
     */
    Map<String, DfBootDatasourceProperties.DatasourceNodeProperties> loadConfig(DataSource dataSource);
}
