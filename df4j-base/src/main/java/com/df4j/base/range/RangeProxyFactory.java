package com.df4j.base.range;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Range代理类的工程类
 */
public class RangeProxyFactory {

    /**
     * 创建range代理类
     * @param clazz 代理类的父类
     * @param fromObject 创建代理类的模板，会复制属性
     * @param <T> 代理类父类的泛型参数
     * @return
     */
    public static <T> T create(Class<T> clazz, T fromObject){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setInterfaces(new Class[]{Range.class});
        enhancer.setCallback(new RangeProxyMethodInterceptor());
        T toObject = (T) enhancer.create();
        if(fromObject != null){
            BeanUtils.copyProperties(fromObject, toObject);
        }
        return toObject;
    }

    /**
     * 创建range代理类
     * @param clazz 代理类的父类
     * @param <T> 代理类父类的泛型参数
     * @return
     */
    public static <T> T create(Class<T> clazz){
        return create(clazz, null);
    }
}
