package com.df4j.socket.core.config;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 客户端配置
 */
public class ClientConfiguration {

    /**
     * 客户端连接类型 0：单节点 1:主备 2: 顺序轮询连接 3: 随机重连
     */
    private Integer mode = 0;

    /**
     * 服务器地址
     * 当 mode = 0 时，只取第一个
     * 当 mode = 1 时，第一个为主，其余为备
     * 当 mode = 2 时，连接时，顺序连接
     */
    private List<InetSocketAddress> servers;

    /**
     * 是否保持长连接 0: 不保持 1：保持
     */
    private Integer keepAlive = 0;

    /**
     * 连接超时，单位ms,默认-1，无超时时间
     */
    private Long connectTimeOut = -1L;

    /**
     * 读超时 ,单位ms,默认-1，无超时时间
     */
    private Long readTimeOut = -1L;

    /**
     * 连接间隔
     */
    private Long retryConnectInterval = 1000L;

    /**
     * 重连次数
     */
    private Integer retryConnectTimes = 10;

    /**
     * 是否启用心跳
     */
    private boolean enableHeartBeat = false;

    /**
     * 心跳间隔
     */
    private Long heartBeatInterval = 10000L;

    /**
     * 心跳失败累计heartBeatFailTimes次断开连接
     */
    private int heartBeatFailTimes = 10;

    /**
     * 单个协议包最大大小
     */
    private int maxPackageSize = 8 * 1024 * 1024;

    /**
     * 构造方法
     */
    public ClientConfiguration() {
    }

    /**
     * 构造方法
     * @param mode
     * @param servers
     * @param keepAlive
     * @param connectTimeOut
     * @param readTimeOut
     * @param retryConnectInterval
     * @param retryConnectTimes
     * @param enableHeartBeat
     * @param heartBeatInterval
     * @param heartBeatFailTimes
     */
    public ClientConfiguration(Integer mode, List<InetSocketAddress> servers, Integer keepAlive, Long connectTimeOut, Long readTimeOut, Long retryConnectInterval, Integer retryConnectTimes, boolean enableHeartBeat, Long heartBeatInterval, int heartBeatFailTimes) {
        this.mode = mode;
        this.servers = servers;
        this.keepAlive = keepAlive;
        this.connectTimeOut = connectTimeOut;
        this.readTimeOut = readTimeOut;
        this.retryConnectInterval = retryConnectInterval;
        this.retryConnectTimes = retryConnectTimes;
        this.enableHeartBeat = enableHeartBeat;
        this.heartBeatInterval = heartBeatInterval;
        this.heartBeatFailTimes = heartBeatFailTimes;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public List<InetSocketAddress> getServers() {
        return servers;
    }

    public void setServers(List<InetSocketAddress> servers) {
        this.servers = servers;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Long getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Long getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(Long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public Long getRetryConnectInterval() {
        return retryConnectInterval;
    }

    public void setRetryConnectInterval(Long retryConnectInterval) {
        this.retryConnectInterval = retryConnectInterval;
    }

    public Integer getRetryConnectTimes() {
        return retryConnectTimes;
    }

    public void setRetryConnectTimes(Integer retryConnectTimes) {
        this.retryConnectTimes = retryConnectTimes;
    }

    public boolean isEnableHeartBeat() {
        return enableHeartBeat;
    }

    public void setEnableHeartBeat(boolean enableHeartBeat) {
        this.enableHeartBeat = enableHeartBeat;
    }

    public Long getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(Long heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public int getHeartBeatFailTimes() {
        return heartBeatFailTimes;
    }

    public void setHeartBeatFailTimes(int heartBeatFailTimes) {
        this.heartBeatFailTimes = heartBeatFailTimes;
    }

    public int getMaxPackageSize() {
        return maxPackageSize;
    }

    public void setMaxPackageSize(int maxPackageSize) {
        this.maxPackageSize = maxPackageSize;
    }
}
