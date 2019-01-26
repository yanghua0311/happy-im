package com.happy.im.service.impl;

import com.happy.im.model.Group;
import com.happy.im.service.GroupService;
import com.happy.im.service.common.AbstractService;
import org.springframework.stereotype.Service;

/**
 * Created by youngwa on 2019/01/23. 21:37
 */

@Service("group")
public class GroupServiceImpl extends AbstractService<Group> implements GroupService {
    @Override
    public boolean verification(Group group) {
        return true;
    }
}
