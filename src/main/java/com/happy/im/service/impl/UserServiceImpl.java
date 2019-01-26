package com.happy.im.service.impl;

import com.happy.im.annotation.Operation;
import com.happy.im.model.User;
import com.happy.im.service.UserService;
import com.happy.im.service.common.AbstractService;
import org.springframework.stereotype.Service;

/**
 * Created by youngwa on 2019/01/22. 23:10
 */

@Service("user")
public class UserServiceImpl extends AbstractService<User> implements UserService  {


    @Override
    @Operation("login")
    public boolean login(String content) {
        System.out.println("成功登录");
        User user = convertToModel(content);
        return false;
    }

    @Override
    public boolean verification(User user) {
        System.out.println("成功校验");
        return true;
    }
}
