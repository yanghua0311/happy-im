package com.happy.im;

import com.happy.im.dao.GroupDao;
import com.happy.im.dao.GroupUserDao;
import com.happy.im.dao.UserDao;
import com.happy.im.model.Group;
import com.happy.im.model.User;
import com.happy.im.service.UserService;
import com.happy.im.transport.event.MessageEvent;
import com.happy.im.transport.netty.NettyConfig;
import com.happy.im.transport.netty.NettyConfigImpl;
import com.happy.im.transport.protocol.MessageHolder;
import com.happy.im.util.CommonFactory;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HappyImApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyImApplication.class, args);
//		NettyConfig nettyConfig = CommonFactory.getBean(NettyConfig.class);
//		UserDao userDao = CommonFactory.getBean(UserDao.class);
		User user = new User();
		user.setId(1L);
		user.setName("youngwa");
		user.setAge(24);
		user.setSex(1);

		ApplicationContext applicationContext = CommonFactory.getApplicationContext();
		MessageHolder messageHolder = new MessageHolder();
		messageHolder.setBody("{'name' : 'youngwa'}");
		messageHolder.setType(Byte.valueOf("01"));
		messageHolder.setSign(Byte.valueOf("99"));
		applicationContext.publishEvent(new MessageEvent(messageHolder));
		System.out.println("====");
	}

}

