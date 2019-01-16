package com.df4j.base.jdbc;

/**
 * Id生成接口
 * @param <T>
 */
public interface IdGenerator<T> {
    /**
     * 生成下一个ID
     * @param key
     * @return
     */
    T next(String key);
}
