package com.df4j.boot.datasource;

import com.df4j.base.exception.DfException;

/**
 * 数据源类型
 */
public enum DataSourceType {

    HIKARI(
            "hikari",
            "com.zaxxer.hikari.HikariDataSource",
            null
    ),
    DBCP2(
            "dbcp2",
            "org.apache.commons.dbcp2.BasicDataSource",
            null
    ),
    DRUID(
            "druid",
            "com.alibaba.druid.pool.DruidDataSource",
            null
    ),
    C3P0(
            "c3p0",
            "com.zaxxer.hikari.HikariDataSource",
            "init"
    )
    ;

    private String name;
    private String fullName;
    private String initMethodName;

    private DataSourceType(String name, String fullName, String initMethodName){
        this.name = name;
        this.fullName = fullName;
        this.initMethodName = initMethodName;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public static DataSourceType getDataSourceType(String name){
        try {
            return DataSourceType.valueOf(name.toUpperCase());
        }catch (Exception e){
            throw new DfException("[" + name + "]数据源类型不匹配");
        }
    }
}
