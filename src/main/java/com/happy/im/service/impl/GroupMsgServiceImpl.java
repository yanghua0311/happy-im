package com.happy.im.service.impl;

import com.happy.im.model.GroupMsg;
import com.happy.im.service.GroupService;
import com.happy.im.service.common.AbstractService;
import org.springframework.stereotype.Service;

/**
 * Created by youngwa on 2019/01/23. 21:38
 */

@Service("groupMsg")
public class GroupMsgServiceImpl extends AbstractService<GroupMsg> implements GroupService {
    @Override
    public boolean verification(GroupMsg groupMsg) {
        return true;
    }
}
