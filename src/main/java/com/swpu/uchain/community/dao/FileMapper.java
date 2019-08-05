package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.entity.File;
import java.util.List;

public interface FileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    File selectByPrimaryKey(Integer id);

    List<File> selectAll();

    int updateByPrimaryKey(File record);
}