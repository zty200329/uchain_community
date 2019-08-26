package com.swpu.uchain.community.service.impl;


import com.swpu.uchain.community.dao.ProBriefMapper;
import com.swpu.uchain.community.entity.ProBrief;
import com.swpu.uchain.community.service.ProBriefService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zty
 * @date: 2019/8/9 18:24
 * 描述：
 */
@Transactional
@Service
@Slf4j
public class ProBriefServiceImpl implements ProBriefService {
    @Autowired
    private ProBriefMapper proBriefMapper;

     /**
     * 根据
     * @param id
     * @return
     */


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return proBriefMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProBrief record) {
        return proBriefMapper.insert(record);
    }

    @Override
    public ProBrief selectByPrimaryKey(Integer id) {
        return proBriefMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProBrief> selectAll() {
        return proBriefMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ProBrief record) {
        return proBriefMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByProName(String proName) {
        return proBriefMapper.deleteByProName(proName);
    }

    @Override
    public String findUrlByProName(String proName) {
        return proBriefMapper.findUrlByProName(proName);
    }

    @Override
    public List<ProBrief> selectByFileTypeId(Integer id) {
        return  proBriefMapper.selectByFileTypeId(id);
    }

    @Override
    public ProBrief findByProName(String proName) {
        return proBriefMapper.findByProName(proName);
    }

    @Override
    public Integer isProExist(String proName){
        ProBrief proBrief = findByProName(proName);
        Integer integer = 1;
        if (proBrief!=null){
            integer = 0;
        }
        return integer;
    }

}
