package com.happy.im.dao;

import com.happy.im.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserDao {

    @Select("select * from user where name = #{name}")
    User getUser(@Param("name") String name);

    @Insert("INSERT INTO user(name,sex,age,create_date,update_date) " +
            "values(#{user.name},#{user.sex},#{user.age},#{user.createDate},#{user.updateDate})")
    int insert(@Param("user") User user);
}
