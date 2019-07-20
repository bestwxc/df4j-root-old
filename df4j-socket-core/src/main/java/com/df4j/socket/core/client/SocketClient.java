package com.df4j.socket.core.client;

import com.df4j.socket.core.config.ClientConfiguration;

/**
 * SocketClient接口
 */
public interface SocketClient<T>{

    String getName();
    /**
     * 配置
     * @param configuration
     */
    void config(ClientConfiguration configuration);

    /**
     * 重置，将连接重置为新建状态
     */
    void reset();

    /**
     * 初始化
     */
    void init();

    /**
     * 连接服务器
     */
    void connect();

    /**
     * 重连
     */
    void reconnect();

    /**
     * 心跳
     */
    void heatBeat();

    /**
     * 关闭连接
     */
    void close();

    /**
     * 获取连接
     * @return
     */
    T getConnection();

    /**
     * 归还连接
     */
    void returnConnect();
}
