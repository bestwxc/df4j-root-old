package com.df4j.socket.netty;

import com.df4j.socket.core.client.AbstractSocketClient;
import com.df4j.socket.core.config.ClientConfiguration;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import java.util.concurrent.ExecutorService;

/**
 * 抽象的NettySocketClient客户端
 */
public class AbstractNettySocketClient extends AbstractSocketClient<ChannelHandlerContext> {

    private Logger logger = LoggerFactory.getLogger(AbstractNettySocketClient.class);

    private Class socketChannelClass;

    private EventLoopGroup eventLoopGroup;

    private Bootstrap bootstrap;

    private ChannelHandlerContext channelHandlerContext;

    public AbstractNettySocketClient(String name, ExecutorService executorService, ClientConfiguration configuration,
                                     Class socketChannelClass, EventLoopGroup eventLoopGroup) {
        super(name, executorService, configuration);
        this.socketChannelClass = socketChannelClass;
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    public ChannelHandlerContext getConnection0() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    protected void init0() {
        Assert.notNull(this.eventLoopGroup, "eventLoopGroup不能为null");
        Assert.notNull(this.socketChannelClass,"socketChannelClass不能为null");
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(eventLoopGroup)
                .channel(socketChannelClass)
                .option(ChannelOption.TCP_NODELAY, false);
        this.config(this.bootstrap);
    }

    /**
     * 配置
     * @param bootstrap
     */
    protected void config(Bootstrap bootstrap){}

    @Override
    protected void connect0() {
        ChannelFuture future = this.bootstrap.connect(this.getCurrentServer());
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    logger.info("[连接-{}]连接成功", getName());
                } else {
                    logger.info("[连接-{}]连接失败", getName());
                }
            }
        });
        try {
            future.sync();
        }catch (Exception e){
            logger.error("[连接-" + this.getName() + "]连接出现异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void close0() {
        this.getConnection().close();
    }
}
