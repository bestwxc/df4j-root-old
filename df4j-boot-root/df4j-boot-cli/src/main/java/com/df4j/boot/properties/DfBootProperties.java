package com.df4j.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SpringBoot配置汇总
 */
@ConfigurationProperties(prefix = "df.boot")
public class DfBootProperties {
    private DfBootCliProperties cli;
    private DfBootWebProperties web;
    private DfBootDatasourceProperties datasource;
    private DfBootMybatisProperties mybatis;
    private DfBootRedisProperties redis;
    private DfBootCloudProperties cloud;

    public DfBootCliProperties getCli() {
        return cli;
    }

    public void setCli(DfBootCliProperties cli) {
        this.cli = cli;
    }

    public DfBootWebProperties getWeb() {
        return web;
    }

    public void setWeb(DfBootWebProperties web) {
        this.web = web;
    }

    public DfBootDatasourceProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(DfBootDatasourceProperties datasource) {
        this.datasource = datasource;
    }

    public DfBootMybatisProperties getMybatis() {
        return mybatis;
    }

    public void setMybatis(DfBootMybatisProperties mybatis) {
        this.mybatis = mybatis;
    }

    public DfBootRedisProperties getRedis() {
        return redis;
    }

    public void setRedis(DfBootRedisProperties redis) {
        this.redis = redis;
    }

    public DfBootCloudProperties getCloud() {
        return cloud;
    }

    public void setCloud(DfBootCloudProperties cloud) {
        this.cloud = cloud;
    }
}
