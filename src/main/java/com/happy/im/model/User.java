package com.happy.im.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youngwa on 2019/01/15. 21:25
 */
@Data
public class User extends BaseModel {

    private String name;

    private int sex;   //0女 1男

    private int age;

}
