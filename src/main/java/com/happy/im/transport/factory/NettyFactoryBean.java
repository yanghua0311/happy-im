package com.happy.im.transport.factory;

import com.happy.im.transport.netty.NettyConfig;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by youngwa on 2019/01/14. 22:05
 */

@Component
public class NettyFactoryBean implements FactoryBean<NettyConfig> {
    @Autowired
    NettyConfig nettyConfig;

    @Override
    public NettyConfig getObject() throws Exception {
        nettyConfig.setParentGroup(1);
        nettyConfig.setChildGroup();
        nettyConfig.setChannel(NioServerSocketChannel.class);
        nettyConfig.setHandler();
        nettyConfig.bind(20000);
        return nettyConfig;
    }

    @Override
    public Class<?> getObjectType() {
        return NettyConfig.class;
    }
}
