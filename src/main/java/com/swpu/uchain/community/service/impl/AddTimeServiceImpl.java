package com.swpu.uchain.community.service.impl;

import com.swpu.uchain.community.dao.AddInfoMapper;
import com.swpu.uchain.community.dto.AddInfoDTO;
import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.entity.AddInfo;
import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.exception.BasicException;
import com.swpu.uchain.community.form.AddInfoForm;
import com.swpu.uchain.community.service.AddTimeService;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.AddBasicInfoVO;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author；lzh
 * @Date:2019/8/613:17 Descirption:
 */
@Service
public class AddTimeServiceImpl implements AddTimeService {

    private final Integer INITAIL_ADDSTATE = new Integer(0);

    @Autowired
    private AddInfoMapper addInfoMapper;

    @Override
    public ResultVO getAllAddInfo() {
        List<AddInfo> addInfoList = addInfoMapper.selectAll();
        return ResultVOUtil.success(addInfoList);
    }

    @Override
    public ResultVO insert(AddInfoForm addInfoForm) {
        AddInfoDTO addInfoDTO = new AddInfoDTO();
        BeanUtils.copyProperties(addInfoForm,addInfoDTO);
        addInfoDTO.setAddState(INITAIL_ADDSTATE);
        int result = addInfoMapper.insert(addInfoDTO);
        if(result != 1){
            throw new BasicException(ResultEnum.SQL_ERROR.getCode(),ResultEnum.SQL_ERROR.getMsg());
        }
        return ResultVOUtil.success(addInfoDTO);
    }

    @Override
    public ResultVO check(AddTimeDTO addTimeDTO) {
        int result = addInfoMapper.updateAddTimeState(addTimeDTO);
        if(result != 1){
            throw new BasicException(ResultEnum.SQL_ERROR.getCode(),ResultEnum.SQL_ERROR.getMsg());
        }
        return ResultVOUtil.success(addTimeDTO);
    }

    @Override
    public ResultVO getAddList(String userId) {
        AddBasicInfoVO addBasicInfoVO = addInfoMapper.getSelfAddInfo(userId);
        if(addBasicInfoVO == null){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.success(addBasicInfoVO);
    }
}
