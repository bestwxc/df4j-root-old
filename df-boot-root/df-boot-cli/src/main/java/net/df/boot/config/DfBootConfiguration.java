package net.df.boot.config;

import net.df.boot.properties.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 引入属性配置，集中管理框架属性
 */
@Configuration
@EnableConfigurationProperties({
        DfBootProperties.class,
        DfBootCliProperties.class,
        DfBootWebProperties.class,
        DfBootMybatisProperties.class,
        DfBootRedisProperties.class,
        DfBootDatasourceProperties.class,
        DfBootCloudProperties.class
})
public class DfBootConfiguration {

}
