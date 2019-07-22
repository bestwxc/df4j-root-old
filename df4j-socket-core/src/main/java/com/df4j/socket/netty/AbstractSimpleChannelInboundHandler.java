package com.df4j.socket.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象类，用于设置ChannelHandlerContext
 * @param <T>
 */
public abstract class AbstractSimpleChannelInboundHandler<T> extends SimpleChannelInboundHandler<T> {

    private Logger logger = LoggerFactory.getLogger(AbstractSimpleChannelInboundHandler.class);

    private AbstractNettySocketClient abstractNettySocketClient;

    public AbstractSimpleChannelInboundHandler(AbstractNettySocketClient abstractNettySocketClient) {
        this.abstractNettySocketClient = abstractNettySocketClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[连接-{}]channelActive", abstractNettySocketClient.getName());
        super.channelActive(ctx);
        abstractNettySocketClient.setChannelHandlerContext(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("[连接-{}]channelInactive", abstractNettySocketClient.getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[连接-" + abstractNettySocketClient.getName() + "]异常,关闭连接", cause);
        ctx.close();
    }
}
