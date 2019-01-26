package com.happy.im.transport.event;

import com.happy.im.transport.protocol.MessageHolder;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by youngwa on 2019/01/22. 22:30
 */

@Data
public class MessageEvent extends ApplicationEvent {

    public MessageEvent(MessageHolder source) {
        super(source);
    }
}
