package net.df.base.utils;


import net.df.base.exception.DfException;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean、Map互转工具类
 */
public class BeanMapUtils {

    /**
     * 将对象转换成map
     * @param bean
     * @return
     */
    public static Map<String,?> toMap(Object bean){
        Map<String,Object> map = new HashMap<String,Object>();
        ReflectionUtils.doWithFields(bean.getClass(),field -> {
            field.setAccessible(true);
            map.put(field.getName(), ReflectionUtils.getField(field, bean));
        });
        return map;
    }

    /**
     * 将map转换成对象
     * @param clazz
     * @param map
     * @param <T>
     * @return
     */
    public static <T> T toBean(Class<T> clazz,Map<String,?> map){
        try {
            T t = clazz.newInstance();
            for (String key : map.keySet()) {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(t, map.get(key));
            }
            return t;
        }catch (Exception e){
            throw new DfException("map转换成Bean异常",e);
        }
    }
}
