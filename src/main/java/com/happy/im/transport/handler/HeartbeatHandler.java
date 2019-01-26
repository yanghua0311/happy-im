package com.happy.im.transport.handler;

import com.happy.im.transport.connection.ConnPool;
import com.happy.im.transport.protocol.MessageHolder;
import com.happy.im.transport.protocol.ProtocolHeader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 心跳检测Handler
 * <p>
 *
 * @author Yohann.
 */
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    public static AtomicBoolean isLogout = new AtomicBoolean(false);

    private Channel channel;
    private String username;

    // 丢失的心跳数
    private int counter = 0;

    public HeartbeatHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (username == null) {
                username = ConnPool.query(channel);
            }
            // 心跳丢失
            counter++;
            log.info(username + " 丢失" + counter + "个心跳包");
            if (counter > 4) {
                // 心跳丢失数达到5个，主动断开连接
                ctx.channel().close();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnPool.remove(username);
        if (isLogout.get()) {
            isLogout.set(false);
            log.info(username + " 退出登录");
        } else {
            log.info(username + " 与服务器断开连接");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageHolder) {
            MessageHolder messageHolder = (MessageHolder) msg;
            if (messageHolder.getType() == ProtocolHeader.HEARTBEAT) {
                if (username == null) {
                    username = ConnPool.query(channel);
                }
                // 心跳丢失清零
                counter = 0;
//                logger.info(username + " 收到心跳包");
                ReferenceCountUtil.release(msg);
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }
}