package com.df4j.socket.core.client;

/**
 * 客户端状态
 */
public class ClientStatus {
    /**
     * 新建
     */
    public static Integer NEW = 0;
    /**
     * 初始化完成，关闭状态
     */
    public static Integer CLOSED = 1;
    /**
     * 连接中
     */
    public static Integer CONNECTING = 2;
    /**
     * 连接成功
     */
    public static Integer CONNECTED = 3;
    /**
     * 关闭中
     */
    public static Integer CLOSING = 4;
    /**
     * 待销毁
     */
    public static Integer TO_BE_DESTROYED = 9;
}
