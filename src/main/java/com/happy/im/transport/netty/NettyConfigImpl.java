package com.happy.im.transport.netty;

import com.happy.im.transport.exception.NullParamsException;
import com.happy.im.transport.handler.AcceptorHandler;
import com.happy.im.transport.handler.ProtocolDecoder;
import com.happy.im.transport.handler.ProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by youngwa on 2019/01/14. 21:35
 */

@Component
@Slf4j
public class NettyConfigImpl implements NettyConfig{

    private final ServerBootstrap bootstrap;
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private Class channelClass;

    public NettyConfigImpl() {
        bootstrap = new ServerBootstrap();
    }

    @Override
    public void setParentGroup() {
        parentGroup = new NioEventLoopGroup();
    }

    @Override
    public void setParentGroup(int nThreads) {
        parentGroup = new NioEventLoopGroup(nThreads);
    }

    @Override
    public void setChildGroup() {
        childGroup = new NioEventLoopGroup();
    }

    @Override
    public void setChildGroup(int nThreads) {
        childGroup = new NioEventLoopGroup(nThreads);
    }

    @Override
    public void setChannel(Class channelClass) {
        this.channelClass = channelClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setHandler() {
        validate();
        bootstrap.group(parentGroup, childGroup);
        bootstrap.channel(channelClass);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //配置解码器
                pipeline.addLast("ProtocolDecoder", new ProtocolDecoder());
                //配置编码器
                pipeline.addLast("ProtocolEncoder", new ProtocolEncoder());
                pipeline.addLast("IdleStateHandler", new IdleStateHandler(6, 0, 0));
                //处理器
                pipeline.addLast("AcceptorHandler", new AcceptorHandler());
            }
        }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void bind(int port) {
        bind(port, true);
    }

    @Override
    public void bind(int port, boolean sync) {
        ChannelFuture future = null;

        try {
            future = bootstrap.bind(port).sync();
            log.info("服务器启动成功 监听端口(" + port + ")");

            if (sync) {
                future.channel().closeFuture().sync();
            } else {
                future.channel().closeFuture();
            }
            log.info("服务器关闭");

        } catch (InterruptedException e) {
            log.warn("Netty绑定异常", e);
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    private void validate() {
        if (parentGroup == null
                || childGroup == null
                || channelClass == null) {
            throw new NullParamsException("parentGroup == null " +
                    "|| childGroup == null " +
                    "|| channelClass == null");
        }
    }
}
