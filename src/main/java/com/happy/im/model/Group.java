package com.happy.im.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by youngwa on 2019/01/15. 21:34
 */

@Data
public class Group extends BaseModel{
    private String name;
    private long createrId;
    private int membersTotal;
    private List<Long> members;

    public Group() {
        membersTotal = 1;
    }
}
