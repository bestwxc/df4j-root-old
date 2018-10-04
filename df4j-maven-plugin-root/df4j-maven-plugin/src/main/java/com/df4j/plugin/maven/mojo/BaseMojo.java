package com.df4j.plugin.maven.mojo;

import com.df4j.base.utils.JsonConfigurationUtils;
import com.df4j.plugin.maven.config.Configuration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

public abstract class BaseMojo extends AbstractMojo {
    @Parameter(property = "df.plugin.configurationFile",
            defaultValue = "${project.basedir}/src/main/resources/dfConfig.json", required = true)
    protected File configurationFile;


    public Configuration getConfiguration(){
        Configuration configuration = null;
        if(configurationFile == null || !configurationFile.exists()){
            this.getLog().warn("配置文件" + configurationFile.getAbsoluteFile().getAbsolutePath() + "不存在,使用pom默认配置");
            configuration = new Configuration();
        }else{
            this.getLog().info("正在读取配置");
            Resource resource = new FileSystemResource(configurationFile.getAbsolutePath());
            JsonConfigurationUtils<Configuration> jsonConfigurationUtils = new JsonConfigurationUtils<>(resource,Configuration.class);
            configuration = jsonConfigurationUtils.getConfiguration();
            this.getLog().info("配置文件读取成功");
        }
        return configuration;
    }

}
