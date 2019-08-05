package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.entity.AddInfo;
import java.util.List;

public interface AddInfoMapper {
    int deleteByPrimaryKey(Integer addId);

    int insert(AddInfo record);

    AddInfo selectByPrimaryKey(Integer addId);

    List<AddInfo> selectAll();

    int updateByPrimaryKey(AddInfo record);
}