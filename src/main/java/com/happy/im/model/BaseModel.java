package com.happy.im.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youngwa on 2019/01/15. 21:42
 */
@Data
public class BaseModel implements Serializable {
    private long id;
    private Date createDate;
    private Date updateDate;

    public BaseModel() {
        createDate = new Date();
        updateDate = new Date();
    }
}
