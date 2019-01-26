package com.happy.im.service.common;

import com.happy.im.annotation.Operation;
import com.happy.im.service.common.AbstractService;
import com.happy.im.transport.event.MessageEvent;
import com.happy.im.transport.protocol.MessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youngwa on 2019/01/16. 22:18
 */

@Service
public class BusnessDispatchCenter  implements SmartApplicationListener {
    Map<String, AbstractService> specificServiceMap = new HashMap<>();

    @Autowired
    public BusnessDispatchCenter(List<AbstractService> services){
        for (AbstractService service : services) {
            specificServiceMap.put(service.getClass().getDeclaredAnnotation(Service.class).value(), service);
        }
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> sourceClass) {
        return sourceClass == MessageEvent.class;
    }

    @Override
    public boolean supportsSourceType(@Nullable Class<?> sourceType) {
        return true;
    }


    //根据消息类型转发至具体service
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        MessageHolder messageHolder = (MessageHolder) applicationEvent.getSource();
        AbstractService service = specificServiceMap.get("user");
        service.excute(String.valueOf(messageHolder.getBody()),"login");

    }
}
