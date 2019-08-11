package com.swpu.uchain.community.service.impl;

import com.swpu.uchain.community.dto.ExcelInfoDTO;
import com.swpu.uchain.community.service.ExcelService;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.util.DateUtil;
import com.swpu.uchain.community.vo.AllTimeListVo;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author；lzh
 * @Date:2019/8/1014:42 Descirption:
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private SignInService signInService;

    @Override
    public List<ExcelInfoDTO> transferExcel() {
        String startDate = DateUtil.getMonDay();
        String endDate = DateUtil.getSunDay();
        ResultVO resultVO = signInService.getAllTimeByDates(startDate, endDate);
        List<AllTimeListVo> allTimeListVoList = (List<AllTimeListVo>)resultVO.getData();
        ExcelInfoDTO excelInfoDTO = null;
        List<ExcelInfoDTO>  excelInfoDTOList = new ArrayList<>();
        for(AllTimeListVo allTimeListVo: allTimeListVoList) {
            excelInfoDTO = new ExcelInfoDTO();
            excelInfoDTO.setUserName(allTimeListVo.getUserName());
            String[] times = allTimeListVo.getTotalTime().split("-");
            excelInfoDTO.setTotalTime(times[0]+"小时"+times[1]+"分钟");
            excelInfoDTO.setAverageTime(allTimeListVo.getAHours()+"小时"+allTimeListVo.getAMinutes()+"分钟");
            if(allTimeListVo.getIsQualified().equals("Y")) {
                excelInfoDTO.setIsQualify("合格");
            }else{
                excelInfoDTO.setIsQualify("不合格");
            }
            excelInfoDTOList.add(excelInfoDTO);
        }
        return excelInfoDTOList;
    }
}
