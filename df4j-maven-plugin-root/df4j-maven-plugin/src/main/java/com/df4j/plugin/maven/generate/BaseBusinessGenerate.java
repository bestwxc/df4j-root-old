package com.df4j.plugin.maven.generate;

import com.df4j.plugin.maven.config.ModelConfiguration;
import com.df4j.plugin.maven.config.ViewConfiguration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import com.df4j.base.exception.DfException;
import com.df4j.base.utils.FileUtils;
import com.df4j.base.utils.RegexUtils;
import com.df4j.base.utils.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.df4j.base.utils.StringUtils.classNameToObjectName;

public class BaseBusinessGenerate extends BaseGenerator{
    private Logger logger = LoggerFactory.getLogger(BaseBusinessGenerate.class);

    private Configuration cfg = null;

    String separator = "/".equals(File.separator) ? File.separator : "\\\\";

    public BaseBusinessGenerate(){

    }

    public BaseBusinessGenerate(ModelConfiguration configuration){
        super();
        this.setConfiguration(configuration);
    }
    @Override
    public void generate() {
        //初始化freemarker配置文件
        this.initFreeMarkerConfiguration();
        Object root = this.getDataModel();
        String projectPath = this.getConfiguration().getSrcFolder();
        String modelClass = this.getModelClass();
        String serviceClass = this.getServiceClass();
        String controllerClass = this.getControllerClass();
        String serviceFilePath = projectPath + File.separator + serviceClass.replaceAll("\\.", separator) + ".java";
        String controllerFilePath = projectPath + File.separator + controllerClass.replaceAll("\\.", separator) + ".java";
        ViewConfiguration viewConfiguration = this.getConfiguration().getView();
        File viewFolder = new File(viewConfiguration.getViewProjectPath(),
                viewConfiguration.getViewPackage());
        if(!viewFolder.exists()){
            viewFolder.mkdirs();
        }
        if(this.getConfiguration().getService().isEnabled()){
            this.generateFile("service.ftl",root, serviceFilePath);
        }
        if(this.getConfiguration().getController().isEnabled()){
            this.generateFile("controller.ftl",root, controllerFilePath);
        }
        if(this.getConfiguration().getView().isEnabled()){
            File viewFile = new File(viewFolder, viewConfiguration.getViewPathAndName());
            this.generateFile("view.ftl",root, viewFile.getAbsolutePath());
        }
    }

    /**
     * 初始化模板配置文件
     */
    private void initFreeMarkerConfiguration(){
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(this.getClass(),"/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setOutputEncoding("UTF-8");
    }

    /**
     * 获取模板
     * @param ftlName
     * @return
     */
    private Template getTemplate(String ftlName){
        try {
            return cfg.getTemplate(ftlName);
        } catch (IOException e) {
            throw new DfException("模板文件[" + ftlName + "]不存在",e);
        }
    }

    /**
     * 使用模板生成文件
     * @param ftlName
     * @param dataModel
     * @param filePath
     */
    private void generateFile(String ftlName, Object dataModel, String filePath){
        Template template = this.getTemplate(ftlName);
        Writer writer = null;
        try{
            File targetFile = new File(filePath);
            File folder = targetFile.getParentFile();
            if(!folder.exists()){
                folder.mkdirs();
            }
            logger.info("ftlName:{},filePath:{},dataModel:{}",
                    ftlName, targetFile.getAbsoluteFile().getName(),dataModel);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            template.process(dataModel, writer);
            logger.info("文件{}生成成功",filePath);
        }catch (Exception e){
            throw new DfException("生成模板失败",e);
        }finally {
            FileUtils.close(writer);
        }
    }

    public Object getDataModel(){
        String moduleName = this.getConfiguration().getModuleName();
        String modelClassName = this.getConfiguration().getModelClassName();
        boolean isLogicDelete = this.getConfiguration().isLogicDelete();
        String ukColumns = this.getConfiguration().getUkColumns();
        String modelObjectName = classNameToObjectName(modelClassName);
        String modelClass = this.getModelClass();
        String serviceClass = this.getServiceClass();
        String serviceClassName = this.getServiceClassName();
        String serviceObjectName = classNameToObjectName(serviceClassName);
        String controllerClass = this.getControllerClass();
        String controllerClassName = this.getControllerClassName();
        String controllerObjectName = classNameToObjectName(controllerClassName);

        Map<String, Object> root = new HashMap();
        String basePackage = this.getBasePackage();
        String rootPackage = basePackage.substring(0, basePackage.indexOf(".module"));
        root.put("rootPackage", rootPackage);
        root.put("basePackage", basePackage);
        root.put("moduleName", moduleName);
        root.put("modelClassName", modelClassName);
        root.put("isLogicDelete", isLogicDelete);
        root.put("ukColumns", ukColumns);
        root.put("modelObjectName", modelObjectName);
        root.put("modelClass", modelClass);
        root.put("serviceClass", serviceClass);
        root.put("serviceClassName", serviceClassName);
        root.put("serviceObjectName", serviceObjectName);
        root.put("controllerClass", controllerClass);
        root.put("controllerClassName", controllerClassName);
        root.put("controllerObjectName", controllerObjectName);
        //包含所有的字段
        LinkedHashMap<String, String> allFieldMap = new LinkedHashMap();
        //仅包含业务字段，去除id,create_time,update_time
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap();
        String modelFilePath = this.getConfiguration().getSrcFolder() + File.separator + modelClass.replaceAll("\\.",separator) + ".java";
        String modelSource = FileUtils.readFile(modelFilePath);
        List<String> list = RegexUtils.getMatch(modelSource,"private.+?;");
        logger.debug("modelFilePath:{},文件数据：{},解析后数据:{}",modelFilePath, modelSource, list);
        if(list.size() == 0){
            throw new DfException("未从model对象中解析到需要的字段");
        }
        for(String s : list){
            String[] ss = s.split("\\s");
            String key = ss[2].replaceAll(";","");
            allFieldMap.put(key, ss[1]);
            if(!"id".equals(key) && !"createTime".equals(key) && !"updateTime".equals(key)){
                fieldMap.put(key,ss[1]);
            }
        }
        if(!fieldMap.containsKey("flag") || ValidateUtils.isEmptyString(ukColumns)){
            logger.warn("配置开启了逻辑删除，但是未在实体中找到flag,或未配置ukColumns,无法使用逻辑删除");
            root.put("isLogicDelete", false);
        }
        root.put("allFieldMap", allFieldMap);
        root.put("fieldMap", fieldMap);
        //直接将详细的配置放进去
        root.put("config",this.getConfiguration());
        return root;
    }
}
