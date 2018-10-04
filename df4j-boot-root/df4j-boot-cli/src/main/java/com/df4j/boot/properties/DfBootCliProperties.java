package com.df4j.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SpringBoot cli模块集中配置
 */
@ConfigurationProperties(prefix = "df.boot.cli")
public class DfBootCliProperties {
}
