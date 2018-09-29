package net.df.base.utils;

import net.df.base.exception.DfException;
import org.springframework.core.io.Resource;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 基于Json的配置工具类
 * @param <T>
 */
public class JsonConfigurationUtils<T> {
    private Class<T> clazz;
    private Resource resource;
    private T configuration = null;

    private JsonConfigurationUtils(Class<T> clazz){
        this.clazz = clazz;
    }

    /**
     * 构造函数
     * @param resource
     * @param clazz
     */
    public JsonConfigurationUtils(Resource resource, Class<T> clazz){
        this(clazz);
        this.resource = resource;
    }

    /**
     * 读取解析配置类
     * @return
     */
    private T readConfiguration(){
        InputStream is = null;
        try {
            is = resource.getInputStream();
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            String content = new String(data, Charset.forName("UTF-8"));
            T t = JsonUtils.getObjectFromString(content, clazz);
            return t;
        }catch (Exception e){
            throw new DfException("解析配置文件异常");
        }
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
