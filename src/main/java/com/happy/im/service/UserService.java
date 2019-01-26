package com.happy.im.service;

import com.happy.im.model.User;
import com.happy.im.service.common.AbstractService;
import org.springframework.stereotype.Service;

/**
 * Created by youngwa on 2019/01/16. 22:47
 */


public interface UserService {
    boolean login(String content);
}
