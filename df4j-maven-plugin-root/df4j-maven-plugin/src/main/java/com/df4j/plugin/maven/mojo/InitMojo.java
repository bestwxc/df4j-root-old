package com.df4j.plugin.maven.mojo;

import com.df4j.base.exception.DfException;
import com.df4j.base.utils.FileUtils;
import com.df4j.base.utils.JsonConfigurationUtils;
import com.df4j.base.utils.ValidateUtils;
import com.df4j.plugin.maven.config.InitFileConfiguration;
import com.df4j.plugin.maven.config.Configuration;
import com.df4j.plugin.maven.config.InitConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 工程初始化插件
 * 用于初始化项目必须的脚本等信息
 */
@Mojo(name="init")
public class InitMojo extends BaseMojo {



    @Parameter(property = "df.plugin.init.skip", defaultValue = "false")
    private boolean skip;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            this.getLog().info("init is skipped.");
            return;
        }
        Configuration configuration = this.getConfiguration();

        InitConfiguration initConfiguration = configuration.getInit();
        if(ValidateUtils.isNull(initConfiguration) || ValidateUtils.isNull(initConfiguration.getFiles()) || initConfiguration.getFiles().isEmpty()){
            this.getLog().warn("配置文件中未检测到init的配置，使用默认配置");
            initConfiguration = new JsonConfigurationUtils<InitConfiguration>(new ClassPathResource("defaultInitConfig.json"),InitConfiguration.class).getConfiguration();
            configuration.setInit(initConfiguration);
        }
        Map<String, InitFileConfiguration> custFiles = initConfiguration.getFiles();
        if(ValidateUtils.isNull(custFiles) || custFiles.isEmpty()){
            throw new DfException("init配置文件和pom.xml中均未找到详细配置，请检查！！！");
        }
        List<String> shouldNotOverrideFileNames = initConfiguration.getShouldNotOverrideFileNames();
        for(String key: custFiles.keySet()){
            InitFileConfiguration file = custFiles.get(key);
            String fromPath = file.getFromPath();
            String toPath = file.getToPath();
            try {
                //处理文件，异常文件不中断
                this.handleFile(key, fromPath, toPath, shouldNotOverrideFileNames);
            }catch (Exception e){
                this.getLog().error("文件[" + key + "]保存失败",e);
            }
        }
    }

    /**
     * 处理单个文件
     * @param key
     * @param fromPath
     * @param toPath
     * @param shouldNotOverrideFileNames
     */
    private void handleFile(String key, String fromPath, String toPath, List<String> shouldNotOverrideFileNames){
        this.getLog().info("即将处理文件[" + key + "],fromPath:" + fromPath + ",toPath:" + toPath);
        byte[] content = this.downloadFile(key,fromPath);
        InputStream inputStream = null;
        this.getLog().info("文件[" + key + "]下载完成，正在保存,保存位置：" + toPath);
        File file = new File(toPath);
        if(ValidateUtils.notEmpty(shouldNotOverrideFileNames) && shouldNotOverrideFileNames.contains(key) && file.exists()){
            this.getLog().warn("文件[" + key + "]存在，不应该覆盖，跳过该文件");
            return;
        }
        try {
            inputStream = new ByteArrayInputStream(content);
            FileUtils.saveFile(inputStream, file);
        }finally {
            FileUtils.close(inputStream);
        }
        this.getLog().info("文件[" + key + "]保存成功，保存路径:" + file.getAbsolutePath());
    }

    /**
     * 下载文件
     * @param key
     * @param url
     * @return
     */
    private byte[] downloadFile(String key, String url){
        ResponseEntity<byte[]> responseEntity = this.restTemplate.getForEntity(url, byte[].class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            byte[] content = responseEntity.getBody();
            return content;
        }else{
            throw new DfException("下载[" + key + "]失败,下载地址:" + url);
        }
    }
}
