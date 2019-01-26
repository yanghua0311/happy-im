package com.happy.im.dao;

import com.happy.im.model.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by youngwa on 2019/01/15. 21:50
 */
@Mapper
public interface GroupDao {


    @Insert("insert into `group` (creater_id,name,members_total,create_date,update_date) " +
            " values(#{group.createrId},#{group.name}, #{group.membersTotal}, #{group.createDate}, #{group.updateDate}) ")
    int insert(@Param("group") Group group);
}
