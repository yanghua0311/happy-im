package com.happy.im.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by youngwa on 2019/01/15. 21:35
 */
@Data
public class GroupMsg extends BaseModel{
    private String msg;
    private long sendUserId;
    private long groupId;
}
