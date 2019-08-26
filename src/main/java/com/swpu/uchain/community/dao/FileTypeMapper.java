package com.swpu.uchain.community.dao;


import com.swpu.uchain.community.entity.FileType;

import java.util.List;

public interface FileTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileType record);

    FileType selectByPrimaryKey(Integer id);

    List<FileType> selectAll();

    int updateByPrimaryKey(FileType record);
}