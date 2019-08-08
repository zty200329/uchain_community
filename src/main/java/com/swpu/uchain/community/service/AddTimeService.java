package com.swpu.uchain.community.service;

import com.swpu.uchain.community.dto.AddInfoDTO;
import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.form.AddInfoForm;
import com.swpu.uchain.community.vo.ResultVO;


/**
 * @author；lzh
 * @Date:2019/8/613:15 Descirption:
 */
public interface AddTimeService {

    /**
     * 获取补签列表
     * @return
     */
    ResultVO getAllAddInfo();

    /**
     * 创建一个补签信息
     * @return
     */
    ResultVO insert(AddInfoForm addInfoForm);

    /**
     * 管理员审核补签
     * @return
     */
    ResultVO check(AddTimeDTO addTimeDTO);

    /**
     * 用户查看自己补签信息
     */
    ResultVO getAddList(String userId);

}
