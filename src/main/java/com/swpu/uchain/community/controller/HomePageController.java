package com.swpu.uchain.community.controller;



import com.swpu.uchain.community.entity.ProBrief;
import com.swpu.uchain.community.service.ProBriefService;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.HomePageVO;
import com.swpu.uchain.community.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zty
 * @date: 2019/8/9 18:49
 * 描述：
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
public class HomePageController {
    @Autowired
    private ProBriefService proBriefService;

    @GetMapping("/fileHome")
    public ResultVO findByProTypeId(
            @RequestParam("proTypeId") Integer proTypeId){
        /**
         * 查询分组所有项目信息
         */
        List<ProBrief> proBriefs = proBriefService.selectByFileTypeId(proTypeId);


        /**
         * 数据拼装
         */
//        List<HomePageVO> pageVOList = new ArrayList<>();
//        for (ProBrief proBrief : proBriefs){
//            HomePageVO homePageVO = new HomePageVO();
//            homePageVO.setProName(proBriefs.getProName());
//            homePageVO.setProUploadTime(proBriefs.getProUploadTime());
//            homePageVO.setProUserId(proBriefs.getProUserId());
//        }
        List<HomePageVO> pageVOList = new ArrayList<>();
        for(ProBrief proBrief : proBriefs){
            HomePageVO homePageVO = new HomePageVO();
            homePageVO.setProName(proBrief.getProName());
            homePageVO.setProUploadTime(proBrief.getProUploadTime());
            homePageVO.setProUserId(proBrief.getProUserId());
            pageVOList.add(homePageVO);
        }
        return ResultVOUtil.success(pageVOList);
    }
}
