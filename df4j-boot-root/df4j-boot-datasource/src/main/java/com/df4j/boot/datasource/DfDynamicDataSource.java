package com.df4j.boot.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DfDynamicDataSource extends AbstractRoutingDataSource {

    private Logger logger = LoggerFactory.getLogger(DfDynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String key = DataSourceNodeManager.getDataSourceKey();
        logger.info("从[{}]数据源获取连接", key);
        return key;
    }
}
