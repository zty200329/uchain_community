package com.swpu.uchain.community.service;


import com.swpu.uchain.community.entity.File;

import java.util.List;

/**
 * @author zty
 * @date: 2019/8/4 11:20
 * 描述：
 */
public interface FileService {
    public int deleteById(Integer id);

    public int insertFile(File record);

    public File selectById(Integer id);

    public List<File> findAll();

    public int updateById(File record);

    int deleteByUrl(String url);
}
