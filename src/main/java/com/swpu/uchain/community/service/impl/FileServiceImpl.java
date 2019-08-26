package com.swpu.uchain.community.service.impl;


import com.swpu.uchain.community.dao.FileMapper;
import com.swpu.uchain.community.entity.File;
import com.swpu.uchain.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zty
 * @date: 2019/8/4 11:27
 * 描述：
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int deleteById(Integer id) {
        return fileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertFile(File record) {
        return fileMapper.insert(record);
    }

    @Override
    public File selectById(Integer id) {
        return fileMapper.selectByPrimaryKey(id)  ;
    }

    @Override

    public List<File> findAll() {
        return fileMapper.selectAll();
    }

    @Override
    public int updateById(File record) {
        return fileMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByUrl(String url) {
        return fileMapper.deleteByUrl(url);
    }
}
