package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.entity.TimeInfo;
import java.util.List;

public interface TimeInfoMapper {
    int deleteByPrimaryKey(String timeId);

    int insert(TimeInfo record);

    TimeInfo selectByPrimaryKey(String timeId);

    List<TimeInfo> selectAll();

    int updateByPrimaryKey(TimeInfo record);
}