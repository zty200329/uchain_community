package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User getUserByName(String userName);

    List<String> getAllUserName();

    String getUserIdByName(String userName);

    User getUserByStuId(String stuId);
}