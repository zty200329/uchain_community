package com.swpu.uchain.community.service;



import com.swpu.uchain.community.entity.ProBrief;

import java.util.List;

/**
 * @author zty
 * @date: 2019/8/9 18:23
 * 描述：
 */
public interface ProBriefService {
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
    /**
     *w
     * @param proName
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

    Integer isProExist(String proName);

}
