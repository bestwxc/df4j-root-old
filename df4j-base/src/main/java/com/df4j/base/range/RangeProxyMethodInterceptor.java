package com.df4j.base.range;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * range代理对象的方法拦截器
 * @param <T>
 */
public class RangeProxyMethodInterceptor<T> implements MethodInterceptor,Range<T> {

    /**
     * 用于存放代理对象的rangtype
     */
    private int rangeType = RangeType.EQUAL;

    /**
     * 代理对象的toRange
     */
    private T toRange;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(Range.class.equals(method.getDeclaringClass())){
            return method.invoke(this, objects);
        } else {
            return methodProxy.invokeSuper(o, objects);
        }
    }

    @Override
    public void setRangeType(int rangeType) {
        this.rangeType = rangeType;
    }

    @Override
    public int getRangeType() {
        return this.rangeType;
    }

    @Override
    public T getToRange() {
        return this.toRange;
    }

    @Override
    public void setToRange(T toRange) {
        this.toRange = toRange;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
