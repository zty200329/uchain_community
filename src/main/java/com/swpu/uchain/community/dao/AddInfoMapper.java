package com.swpu.uchain.community.dao;

import com.swpu.uchain.community.dto.AddInfoDTO;
import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.entity.AddInfo;
import com.swpu.uchain.community.vo.AddBasicInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface AddInfoMapper {
    int deleteByPrimaryKey(Integer addId);

    int insert(AddInfoDTO addInfoDTO);

    AddInfo selectByPrimaryKey(Integer addId);

    List<AddInfo> selectAll();

    int updateByPrimaryKey(AddInfo record);

    List<AddInfo> getAllByUserId(String userId);

    int updateAddTimeState(AddTimeDTO addTimeDTO);

    int deleteOneWeekAgo(@Param("startDate") String startDate, @Param("endDate") String endDate);

    AddBasicInfoVO getSelfAddInfo(String userId);
}