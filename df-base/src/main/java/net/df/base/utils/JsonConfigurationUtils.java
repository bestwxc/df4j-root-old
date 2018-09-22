package net.df.base.utils;

import net.df.base.exception.DfException;
import java.io.File;
import java.lang.reflect.ParameterizedType;

/**
 * 基于Json的配置工具类
 * @param <T>
 */
public class JsonConfigurationUtils<T> {
    private Class<T> clazz;
    private File file;
    private T configuration = null;

    private JsonConfigurationUtils(){
        //获取实际的class类型
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 构造函数
     * @param filePath
     */
    public JsonConfigurationUtils(String filePath){
        this(new File(filePath));
    }

    /**
     * 构造函数
     * @param file
     */
    public JsonConfigurationUtils(File file){
        if(!file.exists()){
            throw new DfException("指定的Json配置文件不存在");
        }
        this.file = file;
    }

    /**
     * 读取解析配置类
     * @return
     */
    private T readConfiguration(){
        String content = FileUtils.readFile(file);
        T t = JsonUtils.getObjectFromString(content,clazz);
        return t;
    }

    /**
     * 返回解析后的配置类
     * @param reload
     * @return
     */
    public synchronized T getConfiguration(boolean reload){
        if(configuration != null && !reload){
            return this.configuration;
        }
        return this.readConfiguration();
    }

    /**
     * 返回解析后的配置类
     * @return
     */
    public T getConfiguration(){
        return this.getConfiguration(false);
    }
}
