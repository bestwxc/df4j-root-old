package com.df4j.plugin.maven.mojo;

import com.df4j.base.exception.DfException;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.plugin.maven.config.ModelConfiguration;
import com.df4j.plugin.maven.config.Configuration;
import com.df4j.plugin.maven.config.GeneratorConfiguration;
import com.df4j.plugin.maven.generate.BaseBusinessGenerate;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.util.Map;

/**
 * 生成插件
 * 可以生成Service,Controller,view等
 */
@Mojo(name="generate")
public class GenerateMojo extends BaseMojo {

    @Parameter(property = "df.plugin.generate.skip", defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            this.getLog().info("generate is skipped.");
            return;
        }
        Configuration configuration = this.getConfiguration();
        GeneratorConfiguration generatorConfiguration = configuration.getGenerate();
        if(ValidateUtils.isNull(generatorConfiguration)){
            throw new DfException("generate配置文件不存在");
        }
        Map<String, ModelConfiguration> models = generatorConfiguration.getModels();
        if(ValidateUtils.isNull(models) || models.isEmpty()){
            this.getLog().warn("未找到识别的配置，未生成任何文件");
            return;
        }
        for(String key: models.keySet()){
            ModelConfiguration modelConfiguration = models.get(key);
            BaseBusinessGenerate businessGenerate = new BaseBusinessGenerate(modelConfiguration);
            businessGenerate.generate();
            this.getLog().info(key + "生成成功");
        }
        this.getLog().info("生成成功");
    }
}
