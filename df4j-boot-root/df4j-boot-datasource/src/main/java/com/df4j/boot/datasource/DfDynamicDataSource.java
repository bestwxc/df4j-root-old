package com.df4j.boot.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DfDynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceNodeManager.getDataSourceKey();
    }
}
