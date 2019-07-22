package com.df4j.socket.core.client;

import com.df4j.socket.core.config.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import static com.df4j.socket.core.client.ClientStatus.*;

/**
 * 抽象类SocketClient
 */
public abstract class AbstractSocketClient<T> implements SocketClient<T>{

    private Logger logger = LoggerFactory.getLogger(AbstractSocketClient.class);

    /**
     * 连接名称
     */
    private String name;

    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 配置
     */
    private ClientConfiguration configuration;

    /**
     * 当前连接的Server
     */
    private InetSocketAddress currentServer;

    /**
     *
     * 状态 0：新建 1：初始化完成，关闭状态 2：连接中 3：连接成功 4：关闭中 9：待销毁
     */
    private Integer status = NEW;

    /**
     * 是否已经连接
     */
    private boolean isConnected = false;

    /**
     * 累计连接失败次数
     */
    private int connectFailTimes = 0;

    /**
     * 连接的时间
     */
    private Long connectTime = -1L;

    /**
     * 上一次心跳时间
     */
    private Long lastHeartBeatTime = -1L;

    /**
     * 心跳累计失败次数
     */
    private int heartBeatFailTimes = 0;

    /**
     * 是否正在使用
     */
    private boolean inUse = false;


    /**
     * 构造方法
     */
    public AbstractSocketClient() {
        this(null);
    }

    /**
     * 构造方法
     * @param name
     */
    public AbstractSocketClient(String name) {
        this(name, null);
    }

    /**
     * 构造方法
     * @param name
     * @param configuration
     */
    public AbstractSocketClient(String name, ClientConfiguration configuration) {
        this(name, null, configuration);
    }

    /**
     * 构造方法
     * @param name
     * @param executorService
     * @param configuration
     */
    public AbstractSocketClient(String name, ExecutorService executorService, ClientConfiguration configuration) {
        this.name = name;
        this.executorService = executorService;
        this.configuration = configuration;
        if(this.configuration == null){
            this.configuration = new ClientConfiguration();
        }
        if(StringUtils.isEmpty(this.name)){
            this.name = this.getClass().getName() + "-" + System.currentTimeMillis();
        }
    }

    protected InetSocketAddress getCurrentServer() {
        return currentServer;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 断言满足状态
     * @param expression
     * @param operName
     */
    public void assertMatchStatus(boolean expression, String operName){
        Assert.isTrue(expression, "[连接-" + this.getName() + "]当前状态为" + this.status + "，不能" + operName);
    }

    /**
     * 打印info日志
     * @param str
     */
    public void info(String str){
        logger.info("[连接-" + this.getName() + "]" + str);
    }

    @Override
    public void config(ClientConfiguration configuration) {
        synchronized (this){
            this.info("开始配置");
            this.assertMatchStatus(status == NEW, "配置");
            this.configuration = configuration;
            this.config0(configuration);
            this.status = CLOSED;
            this.info("配置成功");
        }
    }

    /**
     * 配置方法，子类重写无需考虑同步
     * @param configuration
     */
    protected void config0(ClientConfiguration configuration){}

    @Override
    public void reset(){
        synchronized (this) {
            this.info("开始重置");
            this.assertMatchStatus(status == CLOSED, "重置状态");
            this.reset0();
            this.status = NEW;
            this.info("重置成功");
        }
    }

    /**
     * 重置方法，子类复写无需考虑同步
     */
    protected void reset0(){}


    @Override
    public void init() {
        synchronized (this) {
            this.info("开始初始化");
            this.assertMatchStatus(status == NEW, "初始化");
            Assert.notNull(configuration, "配置属性不能为空");
            Assert.notNull(configuration.getServers(), "连接地址列表不能为空");
            Assert.notEmpty(configuration.getServers(), "连接地址列表不能为空");
            if (configuration.getMode() != 0) {
                Assert.isTrue(configuration.getServers().size() > 1, "配置了多节点模式，但是连接地址列表中的地址数量为1");
            }
            this.init0();
            status = CLOSED;
            this.info("初始化成功");
        }
    }

    /**
     * 初始化方法，子类覆写无需考虑同步
     */
    protected void init0(){}

    @Override
    public void connect() {
        synchronized (this){
            this.info("开始连接");
            this.assertMatchStatus(status == CLOSED, "建立连接");
            this.connectFailTimes++;
            logger.info("[连接-{}]正在进行第{}次连接", this.getName(), this.connectFailTimes);
            this.getNextServer();
            try {
                this.status = CONNECTING;
                this.connect0();
                this.status = CONNECTED;
            }catch (Exception e){
                this.status = CLOSED;
                throw e;
            }
            this.connectFailTimes = 0;
            this.connectTime = System.currentTimeMillis();
            this.info("连接成功");
        }
    }

    /**
     * 连接方法，字类覆写无需考虑同步
     */
    protected void connect0(){}

    @Override
    public void reconnect() {
        synchronized (this) {
            while (true) {
                try{
                    this.connect();
                    break;
                }catch (Exception t){
                    logger.error("[连接-" + this.getName() + "]失败", t);
                }
                if(connectFailTimes >= configuration.getRetryConnectTimes()){
                    logger.warn("[连接={}]重连达到最大次数，重连停止", this.getName());
                    break;
                }
            }
        }
    }

    @Override
    public void close() {
        synchronized (this){
            this.info("关闭连接");
            this.status = CLOSING;
            this.close0();
            this.status = CLOSED;
            this.info("关闭成功");
        }
    }

    /**
     * 关闭连接，提供给子类覆写
     */
    protected void close0() {}

    @Override
    public void heatBeat() {
        if(configuration.isEnableHeartBeat()){
            synchronized (this) {
                this.info("心跳");
                this.heatBeat0();
                this.lastHeartBeatTime = System.currentTimeMillis();
                this.info("心跳完成");
            }
        } else {
            this.info("未开启心跳配置");
        }
    }

    /**
     * 提供给字类进行覆写的心跳
     */
    protected void heatBeat0() {}

    @Override
    public T getConnection() {
        synchronized (this){
            this.info("获取连接");
            this.assertMatchStatus(status == CONNECTED, "获取连接");
            Assert.isTrue(!inUse, "连接正在使用中，不能获取");
            T t = this.getConnection0();
            this.inUse = true;
            this.info("获取连接成功");
            return t;
        }
    }

    /**
     * 获取连接，提供给字类覆写
     * @return
     */
    public abstract T getConnection0();

    @Override
    public void returnConnect() {
        synchronized (this){
            this.info("归还连接");
            this.inUse = false;
            this.info("归还连接成功");
        }
    }

    /**
     * 获取服务器地址
     */
    protected void getNextServer(){
        synchronized (this) {
            int mode = configuration.getMode();
            int index = 0;
            if (mode == 1 || mode == 2) {
                if (currentServer != null) {
                    index = this.configuration.getServers().indexOf(currentServer) + 1;
                    if (index >= this.configuration.getServers().size()) {
                        index -= this.configuration.getServers().size();
                    }
                }
            } else if (mode == 3) {
                // 暂不支持
                logger.warn("[连接-{}]暂不支持该种模式，作为mode = 0 处理", this.getName());
            } else {

            }
            this.currentServer = configuration.getServers().get(index);
            logger.info("[连接-{}]将当前服务器地址设置为：server:{}",this.getName(), this.currentServer);
        }
    }
}
