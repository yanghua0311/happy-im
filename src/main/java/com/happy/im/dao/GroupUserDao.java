package com.happy.im.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupUserDao {

    @Insert("insert into group_user(user_id, group_id) values (#{user_id}, #{group_id})")
    int insert(@Param("group_id") long groupId, @Param("user_id")long userId);
}
