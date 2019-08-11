package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    User getUserByName(String trueName);

    List<String> getAllUserName();

    String getUserIdByName(String userName);

    User getUserByStuId(String stuId);

    int updateTimes(String userId, Integer unQualifyTimes);

    int updateQualifyTime(@Param("userName") String userName, @Param("qualifyTime") Integer qualifyTime);


}