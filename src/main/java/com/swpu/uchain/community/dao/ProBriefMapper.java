package com.swpu.uchain.community.dao;



import com.swpu.uchain.community.entity.ProBrief;

import java.util.List;

public interface ProBriefMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProBrief record);

    ProBrief selectByPrimaryKey(Integer id);

    List<ProBrief> selectAll();

    int updateByPrimaryKey(ProBrief record);

    /**
     * 根据项目名来进行删除
     * @param proName
     * @return
     */
    int deleteByProName(String proName);

    /**
     * SQL构造可能存在问题
     * @param
     * @return
     */
    String findUrlByProName(String proName);

    /**
     * 用于分类 方向
     * @param id
     * @return
     */
    List<ProBrief> selectByFileTypeId(Integer id);

    ProBrief findByProName(String proName);
}