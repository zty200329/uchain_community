package com.swpu.uchain.community.controller;


import com.swpu.uchain.community.entity.ProBrief;
import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.service.ProBriefService;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.DetailsVO;
import com.swpu.uchain.community.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zty
 * @date: 2019/8/14 14:08
 * 描述：详情页面
 */

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
public class DetailsController {
    @Autowired
    private ProBriefService proBriefService;

    @GetMapping("/details")
    public ResultVO findByProName(
            @RequestParam("proName") String proName){
        /**
         * 查询一个项目的具体信息
         */
        ProBrief proBrief = proBriefService.findByProName(proName);

        if(proBrief==null){
            return ResultVOUtil.error(ResultEnum.PROJECT_NOT_EXISI);
        }


        /**
         * 写入VO 返给前端
         */
        DetailsVO detailsVO = new DetailsVO();

        detailsVO.setProName(proBrief.getProName());
        detailsVO.setProUserId(proBrief.getProUserId());
        detailsVO.setProUploadTime(proBrief.getProUploadTime());
        detailsVO.setProDesc(proBrief.getProDesc());

        return ResultVOUtil.success(detailsVO);
    }
}
