package com.happy.im.transport.handler;

import com.happy.im.transport.event.MessageEvent;
import com.happy.im.transport.protocol.MessageHolder;
import com.happy.im.transport.protocol.ProtocolHeader;
import com.happy.im.transport.queue.TaskQueue;
import com.happy.im.util.CommonFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.serviceloader.ServiceFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.util.concurrent.BlockingQueue;

/**
 * 最终接收数据的Handler，将待处理数据放入阻塞队列中，由服务模块take and deal.
 *
 * @author Yohann.
 */
@Slf4j
public class AcceptorHandler extends ChannelInboundHandlerAdapter {

    ApplicationContext applicationContext;

    private final BlockingQueue<MessageHolder> taskQueue;

    public AcceptorHandler() {
        taskQueue = TaskQueue.getQueue();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageHolder) {
            MessageHolder messageHolder = (MessageHolder) msg;
            // 指定Channel
            messageHolder.setChannel(ctx.channel());
            //发布事件
            applicationContext.publishEvent(new MessageEvent(messageHolder));
            // 添加到任务队列
            boolean offer = taskQueue.offer(messageHolder);
            log.info("TaskQueue添加任务: taskQueue=" + taskQueue.size());
            if (!offer) {
                // 服务器繁忙
                log.warn("服务器繁忙，拒绝服务");
                // 繁忙响应
                response(ctx.channel(), messageHolder.getSign());
            }
        } else {
            throw new IllegalArgumentException("msg is not instance of MessageHolder");
        }
    }

    /**
     * 服务器繁忙响应
     *
     * @param channel
     * @param sign
     */
    private void response(Channel channel, byte sign) {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setSign(ProtocolHeader.RESPONSE);
        messageHolder.setType(sign);
        messageHolder.setStatus(ProtocolHeader.SERVER_BUSY);
        messageHolder.setBody("");
        channel.writeAndFlush(messageHolder);
    }

    private void setApplicationContext(){
        applicationContext = CommonFactory.getBean(ApplicationContext.class);
    }
}
